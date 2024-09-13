import numpy as np
import matplotlib.pyplot as plt

#PLOT ALPHA
#alpha = [0.1, 0.5, 1.0]
ka1 = [3.2986722862692828, 3.455751918948772, 3.6128315516282616, 3.7699111843077517, 3.9269908169872414, 4.084070449666731, 4.241150082346221, 4.39822971502571, 4.5553093477052, 4.712388980384689, 4.869468613064179, 5.026548245743669, 5.183627878423159, 5.340707511102649, 5.497787143782138, 5.654866776461628, 5.811946409141117, 5.969026041820607, 6.126105674500097, 6.283185307179586, 6.440264939859075, 6.5973445725385655, 6.754424205218055, 6.911503837897544, 7.0685834705770345, 7.225663103256523, 7.382742735936014, 7.5398223686155035, 7.696902001294992, 7.853981633974483, 8.011061266653972, 8.168140899333462, 8.32522053201295, 8.482300164692441, 8.63937979737193, 8.79645943005142, 8.95353906273091, 9.1106186954104, 9.267698328089889, 9.424777960769378, 9.58185759344887, 9.738937226128359, 9.89601685880785]
gr1 = [0.02346871078201218, 0.022442381827369573, 0.021439585333325795, 0.020467643446151484, 0.019531436411712733, 0.01863388490425777, 0.01777640803539171, 0.016959377622880467, 0.01618231866415165, 0.015444240247562746, 0.01474375400275818, 0.014079203134723883, 0.0134488542370228, 0.012850835809583572, 0.012283310482088083, 0.011744448240271765, 0.011232484708937562, 0.010745712028445098, 0.010282523848704983, 0.00984136626844645, 0.00942081435989195, 0.009019488156457811, 0.008636137232020502, 0.008269558091527074, 0.007918679960948083, 0.007582435067963189, 0.007259881552645453, 0.006950089543359184, 0.006652339546555197, 0.00636575648687554, 0.00608965840888942, 0.0058233704325765245, 0.005566310496615046, 0.0053178715626316375, 0.005077519821693244, 0.004844738768920109, 0.0046191504883559276, 0.004400242504498664, 0.004187631974481576, 0.00398094099099211, 0.0037798517805924803, 0.0035840084913267845, 0.0033931062277972046]
gr2 = [0.02213167095695212, 0.02134332309747755, 0.02056352757097457, 0.0197955086413657, 0.019045156138053798, 0.018315864935393833, 0.017608177787671764, 0.016923807116132394, 0.016261005596650335, 0.015620680432622436, 0.015003640527616824, 0.014410229405172004, 0.013838893877856862, 0.01328881113198932, 0.012757533628326101, 0.012246280754805383, 0.011754989047760844, 0.011284603333891141, 0.010832002713552669, 0.010394775368295583, 0.009972749682436444, 0.009567512369969222, 0.009179912409237545, 0.008807016450106175, 0.008447752581091095, 0.008098270781651706, 0.007762112856969594, 0.007442838996258416, 0.00713200172301498, 0.006833307529753085, 0.0065418253662805625, 0.006261001317181325, 0.005988086626729228, 0.0057290872150979055, 0.005477618422471409, 0.00523145676359249, 0.004990083120464561, 0.004757191304150865, 0.004534749994430598, 0.004316128311970147, 0.004108510719401437, 0.0038989270743741145, 0.0036957637958785483]
gr3 = [0.014641513245075899, 0.015347625371001428, 0.015918566805927625, 0.016372678743030278, 0.01674307365618688, 0.017028046321470924, 0.017222678997033114, 0.017341437267450743, 0.01737376174461147, 0.01733311001473581, 0.017223124432636183, 0.01703879555613419, 0.01680129889230031, 0.01650883047110696, 0.016165521633823232, 0.01578661776185264, 0.015371392310314036, 0.014932551827106622, 0.01447824319491886, 0.01400918631732928, 0.013533574119609426, 0.013054624722391495, 0.012574302611248811, 0.012104535161705954, 0.011635951952172263, 0.011175219941994996, 0.01073090236305596, 0.010258044051058235, 0.009867926217186351, 0.009461047665645594, 0.009057260649438348, 0.008684559931644742, 0.008303622489709284, 0.007937087300451395, 0.0075993075504250385, 0.00725557754532602, 0.0069357626968269415, 0.006634658309614849, 0.006317551273159694, 0.006068058414747668, 0.005752394009860495, 0.005463532348244371, 0.005227800822098061]


#PARAMETERS
eta = 0.0001
Ly = 80 * np.pi
Lz = 2 * np.pi
AR = int(Ly / Lz)
Ny = 128
Nz = 32
nz = 10

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

def plotGrad():
    for i in range(mCrit_min, mCrit_max):
        x = np.gradient(np.log10(kArr[i]))
        plt.plot(tArr, x)

    plt.xlabel('Time')
    plt.ylabel('Growth Rate')


def plotSemilog():
    for i in range(mCrit_min, mCrit_max):
        plt.semilogy(tArr, kArr[i])
        plt.text(tArr[-1] + 0.2, kArr[i][-1], str(i + 1), fontsize='x-small')
    plt.xlabel('Time')
    plt.ylabel('Amplitude')


def plotLoglog():

    plt.loglog(kaRange, growthRate, 'b+', label='Data')
    plt.loglog(kaRange, growthRate, 'b', linewidth=0.8,  label='Fitted')
    plt.loglog(kaRange, coppi, '-.k', label='Analytical Coppi')
    plt.loglog(kaRange, fkr, '--k', label='Analytical FKR')

    plt.xlabel('ka')
    plt.ylabel('Growth rate')
    plt.legend()


if __name__ == '__main__':
    with open('stats/resolution/2048/test00_kw', 'r') as f:
        lines = f.readlines()

    y_num = int(Ny/2)
    time_num = 0

    lineIndex = 0

    time_num = int(len(lines) / (y_num))

    timeArr = [[0 for x in range(y_num)] for y in range(time_num)]
    spaceArr = [[0 for x in range(time_num)] for y in range(y_num)]

    tIndex = 0
    yIndex = 0
    lineIndex = 0

    while lineIndex < len(lines):
        if yIndex == y_num:
            yIndex = 0
            tIndex += 1
        timeArr[tIndex][yIndex] = convertStringToLong(lines[lineIndex].strip())
        yIndex += 1
        lineIndex += 1

    kArr = convertArr(timeArr)
    tArr = np.linspace(0, time_num - 1, time_num)

    tau_a = []
    instability_parameter = []
    tau_eta = (Lz ** 2) / eta

    time = []
    growthRate = []
    kTot = []
    coppi = []
    estimateRes = []
    fkr = []

    mCrit_min = 1
    mCrit_max = 32
    mCrit_IP = int(Ny/2 - 1)
    mCrit_R = int(Ny/2 - 1)

    for i in range(0, int(Ny/2)):
        kTot.append((2*np.pi*(i+1)/Ly))
        instability_parameter.append((2 * (5 - kTot[i]**2) * (3 + kTot[i]**2)) / ((kTot[i]**2) * np.sqrt(4 + kTot[i]**2)))
        tau_a.append(1/(kTot[i]))
        if instability_parameter[i] < 0:
            mCrit_IP = i - 1
            break
    print(tau_eta)
    print(tau_a[16])
    print(instability_parameter[60])

    desiredIndex = 32
    print('Coppi = ' + str((tau_eta / tau_a[desiredIndex])**(1/3) * 3))
    print('FKR = ' + str((tau_eta / tau_a[desiredIndex]) ** (2/5) * (instability_parameter[desiredIndex] * Lz) ** (-1/5) * 3))

    for i in range(0, len(kTot)):
        if instability_parameter[i] > 0:
            x = ((tau_a[i] / tau_eta) ** (-2 / 5)) * ((instability_parameter[i] * Lz) ** (-1 / 5)) * nz
            if x > Nz:
                mCrit_R = i - 1
                break

    kRange = []
    kaRange = []
    instability_parameter_range = []
    tau_a_range = []

    #if mCrit_R <= mCrit_IP:
        #mCrit = mCrit_R
    #else:
        #mCrit = mCrit_IP

    for i in range(mCrit_min, mCrit_max):
        time.append(i)

    for i in range(mCrit_min, mCrit_max):
        kRange.append(kTot[i])
        kaRange.append(kTot[i] * Lz)
        instability_parameter_range.append(instability_parameter[i])
        x = np.gradient(np.log10(kArr[i]))
        growthRate.append(x[-1] * 0.1)
        tau_a_range.append(tau_a[i])
        coppi.append(((tau_a[i])**(-2/3)) * tau_eta**(-1/3))
        fkr.append(((tau_a[i]) ** (-2 / 5)) * (tau_eta ** (-3 / 5)) * ((instability_parameter[i] * Lz) ** (4/5)))



    #print("PARAMETERS:")
    #print("Aspect Ratio = " + str(AR))
    #print("Eta = " + str(eta))
    #print("S = " + str(1/eta))
    #print("Z resolution = " + str(Nz))
    #print("Y resolution = " + str(Ny))
    #print("\nNumber of modes resolved = " + str(mCrit_R))
    #print("Number of modes with positive instability parameter = " + str(mCrit_IP))

    #print("Fastest Growing mode: k_max a = " + str(kaRange[index]) + " growth rate = " + str(growthRate[index]))
    #plotSemilog()
    #plt.plot(tArr, kArr[1])

    maxFreq = []

    for i in range(mCrit_min, mCrit_max):
        w = np.fft.fft(kArr[i])
        freqs = np.fft.fftfreq(len(w))

        wArr = []
        freqsArr = []
        for x in range(0, int(len(w)/2)):
            wArr.append(abs(w[x]))
            freqsArr.append(freqs[x])

        #plt.plot(freqsArr, wArr)
        maxIndex = 2
        for j in range(2, len(wArr)):
            if abs(wArr[maxIndex]) < abs(wArr[j]):
                maxIndex = j
        maxFreq.append(freqsArr[maxIndex])
        #plt.plot(freqsArr[maxIndex], wArr[maxIndex], 'x')

    mode = []
    for i in range(mCrit_min, mCrit_max):
        mode.append(i + 1)

    #plotSemilog()
    x = np.gradient(maxFreq)
    #plotGrad()
    plotLoglog()

    #plt.loglog(ka1, growthRate, label = 'Alpha = 0')
    #plt.loglog(ka1, gr1, label= 'Alpha = 0.1')
    #plt.loglog(ka1, gr2, label = 'Alpha = 0.5')
    #plt.loglog(ka1, gr3, label = 'Alpha = 1')
    #plt.xlabel('mode number (m)')
    #plt.ylabel('Frequency')
    #plt.legend()
    plt.show()