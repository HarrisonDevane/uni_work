import numpy as np
import matplotlib.pyplot as plt
from scipy.optimize import curve_fit

tInitial = 20
tFinal = 30
mCrit = 10
aspectRatio = 5

def convertArr(x):
    iMax = len(x[0])
    jMax = len(x)

    tempArr = [[0 for j in range(jMax)] for i in range(iMax)]

    for i in range(0, iMax):
        for j in range(0, jMax):
            tempArr[i][j] = x[j][i]

    return tempArr


def convertStringToLong(x):
    newString = ''
    magnitudeStr = ''
    index = 1
    for letter in x:
        if letter != 'E':
            newString += letter
            index += 1
        else:
            magnitudeStr = x[index:len(x)]
            break

    newFloat = (float(newString) * 10 ** float(magnitudeStr))
    return newFloat


def func(x, a, b):
    return a * x + b

def measureGrowthRate(kArr):
    relK = []
    tArr = []

    for i in range(tInitial, tFinal):
        tArr.append(i)
        relK.append(np.log10(kArr[i]))

    #plt.plot(tArr, relK)

    popt, pcov = curve_fit(func, tArr, relK)
    return popt[0]


def printPlot(tArr, kArr, ind, tStart, tEnd):
    t1 = []
    k1 = []

    for i in range(tStart, tEnd):
        t1.append(tArr[i])
        k1.append(kArr[i])

    plt.semilogy(t1, k1)
    plt.text(t1[-1] + 0.2, k1[-1], str(ind), fontsize='x-small')


if __name__ == '__main__':
    #with open('stats/exp14/test00_stats_yz', 'r') as f:
    with open('stats/troubleshooting/exp1/test00_stats_yz', 'r') as f:
        lines = f.readlines()

    time_num = 0
    y_num = 0
    z_num = 0

    lineIndex = 0

    while lineIndex < len(lines):
        if lines[lineIndex][2] == ' ':
            y_num += 1
        else:
            break
        lineIndex += 1

    while lineIndex < len(lines):
        if lines[lineIndex][2] != ' ':
            z_num += 1
        else:
            break
        lineIndex += 1

    time_num = int(len(lines) / (y_num + z_num))

    timeArr = [[0 for x in range(y_num)] for y in range(time_num)]
    spaceArr = [[0 for x in range(time_num)] for y in range(y_num)]

    tIndex = 0
    yIndex = 0
    lineIndex = 0

    while lineIndex < len(lines):
        if yIndex == y_num:
            yIndex = 0
            tIndex += 1
            if lineIndex + z_num < len(lines):
                lineIndex += z_num
            else:
                break
        timeArr[tIndex][yIndex] = convertStringToLong(lines[lineIndex].strip())
        yIndex += 1
        lineIndex += 1

    convertArr(timeArr)

    spatialAverage = []
    for i in range(0, time_num):
        temp = 0
        for j in range(0, y_num):
            temp += timeArr[i][j]
        temp = (temp / y_num)
        spatialAverage.append(temp)

    for i in range(0, time_num):
        for j in range(0, y_num):
            timeArr[i][j] -= spatialAverage[i]

    transformedArr = [[0 for x in range(y_num)] for y in range(time_num)]
    absArr = [[0 for x in range(y_num)] for y in range(time_num)]

    transformedArr = np.fft.fft(timeArr)
    tArr = np.linspace(0, time_num - 1, time_num)

    for i in range(0, time_num):
        for j in range(0, y_num):
            absArr[i][j] = abs(transformedArr[i][j])

    spacialAbsArray = convertArr(absArr)
    growthRate = []
    tRange = []

    #for i in range(1, mCrit):
        #printPlot(tArr, spacialAbsArray[i], i, tInitial, tFinal)

    for i in range(1, mCrit):
        x = measureGrowthRate(spacialAbsArray[i])
        tRange.append(2*np.pi*i/aspectRatio)
        growthRate.append(x)

    #**FKR
    plt.loglog(tRange, growthRate)
    plt.xlabel('ka')
    plt.ylabel('Growth Rate')


    print(len(lines))
    #measureGrad(tRange, growthRate)
    #plt.loglog(tRange, growthRate)
    #alpha=2./3.
    #tRange=np.array(tRange)
    #plt.loglog(tRange, tRange**(alpha)*growthRate[0]/tRange[0])
    #print(growthRate)

    plt.show()