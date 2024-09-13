#!/usr/bin/env python

"""
Written by Harrison Devane as part of an MSc (2023-2024).

Script to format and interpret data for luminescence based temperature sensing at VUW.
Takes several .lvm files as an input and outputs formatted csv files, computing such things as
    - Normalization.
    - Averaging.
    - Background-removal.
    - Integration.
"""

import os
import sys
import argparse
import shutil
import numpy as np
import pandas as pd  

def to_csv(filename, df, header_in=True, index_in=True):
    """
    Outputs dataframe as .csv file with specifications.

    Parameters:
    - filename (String): name of the output file.
    - df (DataFrame): dataframe to output as csv.
    - header_in (Boolean): header labels output.
    - index_in (Boolean): index labels output.

    Returns:
    - None
    """

    df.to_csv(filename, sep=',', header=header_in, index=index_in)

def headers_and_index(df):
    """
    Defines a column and index for the dataframe. Necessary as its own function
    as often the indicies and columns themselves require mathematical manipulation.

    Parameters:
    - df (DataFrame): input dataframe

    Returns:
    - formatted_df (DataFrame): the dataframe with  the index set as the first row and 
                                the columns set as the first column.
    """

    formatted_df = df.set_index(list(df)[0])
    formatted_df.columns = formatted_df.iloc[0]
    formatted_df = formatted_df.loc[1:]
    return formatted_df

def normalized_spectrum(df):
    """
    Normalizes an input dataframe such that the max value across the entire dataframe is 1.

    Parameters:
    - df (DataFrame): input dataframe.

    Returns:
    - normalized_df (DataFrame): normalized dataframe.
    """

    normalized_df=(df-df.to_numpy().min())/(df.to_numpy().max()-df.to_numpy().min())
    return normalized_df

def averaged_spectrum(df_list):
    """
    Averages the input dataframes pointwise, and returns the averaged dataframe.

    Parameters:
    - df_list (list(DataFrame)): input n dimensional list of dataframes stored as list.

    Returns:
    - averaged_spectrum (DataFrame): pointwise averaged dataframe.
    """

    avg_df = sum(df for df in df_list) / len(df_list)
    formatted_avg_df = headers_and_index(avg_df)
    return formatted_avg_df

def int_peaks(df_row, int_start, int_end):
    """
    Finds the integrated intensities across a specific range of a dataframe row.

    Parameters:
    - df_row (DataFrame): 1D dataframe to integrate over.
    - int_start (int): Row index to begin integration.
    - int_end (int): Row index to end integration.

    Returns:
    - int_val (int): integer of result of integration.
    """

    reduced_df = df_row.iloc[int_start:int_end]
    x_vals = reduced_df.index.tolist()
    y_vals = reduced_df.values.flatten().tolist()
    int_val = np.trapz(x=x_vals, y=y_vals)
    return int_val

def remove_background(df):
    """
    Subtracts the minimum value of dataframe to each point to remove dark background.

    Parameters:
    - df (DataFrame): dataframe to remove background of.

    Returns:
    - rm_df (DataFrame): input dataframe with background removed.
    """
    
    rm_df = df - df.to_numpy().min()
    return rm_df

def find_max(filename, df):
    """
    Finds row with max value in entire dataframe and outputs this row.
    Used to show the intensity of the largest peak peak changes with temp.
    
    Parameters:
    - filename (String): name of file to output.
    - df (DataFrame): dataframe to find max of.

    Returns:
    - new_frame: 1D dataframw with temperature and max_intensity colummms.
    """

    # Get row with max value in dataframe
    max_index = df.loc[df.max(axis=1).idxmax()]

    # Convert from series to dataframe
    new_frame = max_index.to_frame().reset_index()

    # Rename Columns
    new_frame.columns = new_frame.columns.map(str)
    wavelength = new_frame.columns.values[1]
    new_frame.columns.values[0]='Temperature (name = ' + filename + ', max_peak_wavelength = ' + wavelength + ''
    new_frame.columns.values[1]='Intensity'

    return(new_frame)

def format_file(filepath):
    """
    Takes filepath input, reads the file and formats it into a dataframe.
    
    Parameters:
    - filepath (string) abs path to lvm files.

    Returns:
    - df (DataFrame): the data formatted to a dataframe.
    """

    formatted_list = []
    temperature_list = [0]

    with open(filepath + '.lvm', 'r') as file:

        # Ignore comment lines
        lines = file.readlines()[19:]

        formatted_list = [[] for i in range(len(lines)+1)]

        for index, row in enumerate(lines):
            row_split = row.split('\t')[2:]
            
            # Adds first value of row_split to temperature list
            if row_split[0] != '':
                temperature_list.append(row_split[0])
            
            # Removes first/last values and adds remaining values to the new list
            row_split = row_split[1:-1]
            formatted_list[index+1] = row_split

            formatted_list[0] = temperature_list


    float_list = [list( map(float,i) ) for i in formatted_list]

    # Converts 2D list to dataframe
    df = pd.DataFrame(float_list)
    return df

def run_files(args):
    """
    Main function of file. Adds each file to an active list, and iterates over this list for each command line argument. 
    Any resulting dataframe from the above functions is added to the active list, such that arguments can be mixed and matched.
    
    For example, specifying -a -n -bg will output the averaged, normalized, background removed spectrum.
    
    Parameters:
    - args: command line arguments

    Returns:
    - None
    """

    abs_path = os.path.join(os.getcwd(), args.path)
    
    # if input path doesn't exist
    if not os.path.exists(abs_path):
        sys.exit("Data could not be found")
    
    # Create output folder and remove existing data
    output_path = os.path.join(abs_path, 'output')
    if os.path.exists(output_path):
        shutil.rmtree(output_path)

    os.makedirs(output_path)

    # Adds all files in input directory to list
    file_list = [f[:-4] for f in os.listdir(abs_path)if os.path.isfile(os.path.join(abs_path, f))]

    # Initialize list of dataframe for each file
    formatted_df_list = [pd.DataFrame() for i in range(len(file_list))]

    # Initialize active file list and df list
    active_df_list = []
    active_file_list = []

    # Formats all input files correctly
    for index, filename in enumerate(file_list):
        filepath = os.path.join(args.path, filename)
        formatted_df = format_file(filepath)
        formatted_df_list[index] = formatted_df

    # Computes average spectrum and adds to active list
    if args.average == True:
        avg_spec_df = averaged_spectrum(formatted_df_list)
        active_file_list.append('average_spec')
        active_df_list.append(avg_spec_df)

    # Adds formatted data files to active list
    if args.format == True:
        for index, filename in enumerate(file_list):
            active_file_list.append(filename)
            active_df_list.append(headers_and_index(formatted_df_list[index]))

    # Removes background from spectrum
    if args.bg == True:
        for index, filename in enumerate(active_file_list):
            bg_subtracted = remove_background(active_df_list[index])
            active_df_list[index] = bg_subtracted
            active_file_list[index] = filename + "_bgrm"

    # Normalizes Specturm
    if args.normalize == True:
        for index, filename in enumerate(active_file_list):
            normalized_df = normalized_spectrum(active_df_list[index])
            active_df_list[index] = normalized_df
            active_file_list[index] = filename + "_normalized"

    # Outputs files as csv
    for index, filename in enumerate(active_file_list):
        filepath = os.path.join(output_path, filename) + '_output.csv'
        to_csv(filepath, active_df_list[index])

    # Computes useful peak information
    if args.info == True:
        for index, filename in enumerate(active_file_list):
            df_tranposed = active_df_list[index].transpose()    
            df_peaks = pd.DataFrame(columns=['temp', '7f1', '7f2', '7f3', '7f4'])

            for i, row in df_tranposed.iterrows():
                peak_7f1 = int_peaks(row, 652, 704)                      # 582nm - 600nm 7f1
                peak_7f2 = int_peaks(row, 704, 789)                      # 600nm - 630nm 7f2
                peak_7f3 = int_peaks(row, 831, 870)                      # 645nm - 658nm 7f3
                peak_7f4 = int_peaks(row, 933, 1007)                     # 680nm - 705nm 7f4
                df_peaks.loc[len(df_peaks)] = [i, peak_7f1, peak_7f2, peak_7f3, peak_7f4]

                filepath = os.path.join(output_path, filename) + 'integrated_peaks.csv'
                to_csv(filepath, df_peaks, index_in=False)


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Performs data manipulation on temeprature sensing spectra')
    parser.add_argument('-path', dest='path', required=True, help='The path to the directory containing data files')
    parser.add_argument('-f', dest='format', action='store_true', help='Formats the original files')
    parser.add_argument('-a', dest='average', action='store_true', help='Outputs the averaged spectrum')
    parser.add_argument('-n', dest='normalize', action='store_true', help='Outputs the normalized spectra')
    parser.add_argument('-bg', dest='bg', action='store_true', help='Removes background from spectra (requires path to background file)')
    parser.add_argument('-i', dest='info', action='store_true', help='Outputs integrated peak intensities')

    args = parser.parse_args()
    run_files(args)