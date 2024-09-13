#!/usr/bin/env python

# TODO:
# - Inputs: all spectral files in folder
# - Removes background and normalizes as flags
# - Outputs: columns of integrated peak values (7f1, 7f2, total)


import os
import sys
import argparse
import shutil
import numpy as np
import pandas as pd  
import statistics as stat


def remove_background(df):
    rm_df = df - df.to_numpy().min()
    return rm_df

def int_peaks(df, int_start, int_end):
    # Find values within range
    # Integrate values
    # Return integrated values

    reduced_df = df.iloc[int_start:int_end]
    x_vals = reduced_df.index.tolist()
    y_vals = reduced_df.values.flatten().tolist()
    int_val = np.trapz(x=x_vals, y=y_vals)
    return int_val

def standard_deviation(df, int_start, int_end):
    # Make list of values within range
    # Compute standard deviation
    # Return STD

    reduced_df = df.iloc[int_start:int_end]
    y_vals = reduced_df.values.flatten().tolist()
    std_deviation = stat.stdev(y_vals)
    return std_deviation

def run_files(args):
    abs_path = os.path.join(os.getcwd(), args.path)
    
    # if path doesn't exist
    if not os.path.exists(abs_path):
        sys.exit("Data could not be found")
    
    # Create output folder
    output_path = os.path.join(abs_path, 'output')
    if os.path.exists(output_path):
        shutil.rmtree(output_path)

    os.makedirs(output_path)

    # Adds all files in directory to list
    file_list = [f for f in os.listdir(abs_path)if os.path.isfile(os.path.join(abs_path, f))]

    df_peaks = pd.DataFrame(columns=['name', '7f1', '7f2', '7f3', '7f4', 'total'])

    for filename in file_list:
        with open(os.path.join(args.path, filename), 'r') as file:
            # Store data in dataframe
            lines = file.readlines()[18:-1]
            formatted_list = [[] for i in range(len(lines))]

            for index, row in enumerate(lines):
                row_split = row.strip().split('\t')
                formatted_list[index] = row_split
            
            float_list = [list( map(float,i) ) for i in formatted_list]
            df = pd.DataFrame(float_list)
            df = df.set_index(list(df)[0])

            if args.bg == True:
                df = remove_background(df)
            
            peak_7f1 = int_peaks(df, 652, 704)                      # 582nm - 600nm 7f1
            peak_7f2 = int_peaks(df, 704, 789)                      # 600nm - 630nm 7f2
            peak_7f3 = int_peaks(df, 831, 870)                      # 645nm - 658nm 7f3
            peak_7f4 = int_peaks(df, 933, 1007)                     # 680nm - 705nm 7f4
            total_integrated = int_peaks(df, 0, len(float_list))    # Total

            df_peaks.loc[len(df_peaks)] = [filename, peak_7f1, peak_7f2, peak_7f3, peak_7f4, total_integrated]

    if args.normalize == True:
        for col in df_peaks.columns[1:]:
            df_peaks[col] = (df_peaks[col]-df_peaks[col].min())/(df_peaks[col].max()-df_peaks[col].min())

    if args.std == True:
        df_std = pd.DataFrame(columns=['40', '60', '80', '100', '120', '140', '160', '180'])
        
        for col in df_peaks.columns[1:]:

            # std_40 = standard_deviation(df_peaks[col], 1 , 9) 
            # std_60 = standard_deviation(df_peaks[col], 1, 9)
            # std_80 = standard_deviation(df_peaks[col], 2, 9)
            # std_100 = standard_deviation(df_peaks[col], 2, 9)
            # std_120 = standard_deviation(df_peaks[col], 3, 9)
            # std_140 = standard_deviation(df_peaks[col], 4, 9)
            # std_160 = standard_deviation(df_peaks[col], 5, 9)
            # std_180 = standard_deviation(df_peaks[col], 6, 9)

            std_40 = standard_deviation(df_peaks[col], 600, 850)        # Fixed values for now
            std_60 = standard_deviation(df_peaks[col], 1300, 1600)
            std_80 = standard_deviation(df_peaks[col], 2000, 2300)
            std_100 = standard_deviation(df_peaks[col], 2900, 3200)
            std_120 = standard_deviation(df_peaks[col], 3800, 4000)
            std_140 = standard_deviation(df_peaks[col], 4300, 4700)
            std_160 = standard_deviation(df_peaks[col], 5000, 5500)
            std_180 = standard_deviation(df_peaks[col], 6000, 6300)

            df_std.loc[len(df_std)] = [std_40, std_60, std_80, std_100, std_120, std_140, std_160, std_180]

        df_std_transp = df_std.transpose()
        df_std_transp.columns = ['7f1', '7f2', '7f3', '7f4', 'total']
        df_std_transp.to_csv(os.path.join(output_path, 'std.csv'), sep=',')


    df_peaks.to_csv(os.path.join(output_path, 'output.csv'), sep=',', index=False)


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Performs data manipulation on temeprature sensing spectra')
    parser.add_argument('-path', dest='path', required=True, help='The path to the directory containing data files')
    parser.add_argument('-n', dest='normalize', action='store_true', help='Outputs the normalized spectra')
    parser.add_argument('-bg', dest='bg', action='store_true', help='Removes background from spectra (requires path to background file)')
    parser.add_argument('-std', dest='std', action='store_true', help='finds standard deviation from peaks and outputs to new file (requires path to background file)')

    args = parser.parse_args()
    run_files(args)