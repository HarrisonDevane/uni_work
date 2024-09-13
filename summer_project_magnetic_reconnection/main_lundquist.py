import numpy as np
import scipy.optimize as sc
import matplotlib.pyplot as plt

Ly = 80 * np.pi
Lz = 2 * np.pi
AR = int(Ly / Lz)
Ny = 128
Nz = 2048
nz = 10

def f(x):
    return ((1 / (S[4] * (Lz**2)))**(4/15)) * ((1/x)**(4/15)) * ((((2 * (5 - x**2) * (3 + x**2)) / ((x**2) * np.sqrt(4 + x**2))) * Lz) ** (4/5)) - 1

if __name__ == '__main__':
    k_maxa = [2.199114857512855, 1.8849555921538759, 1.727875959474386, 1.5707963267948966, 1.413716694115407]
    k_analytic = [6.80107867, 5.49596584, 4.99724836, 3.99015852, 3.61769352]
    ratio = []
    S = [10000, 50000, 100000, 500000, 1000000]

    logK = np.log10(k_maxa)
    logS = np.log10(S)
    gr = np.gradient(logK, logS)

    logK2 = np.log10(k_analytic)
    gr2 = np.gradient(logK2, logS)

    plt.loglog(S, k_maxa, label='Simulated')
    plt.loglog(S, k_analytic, label='Analytic')

    plt.xlabel('Lindquist Number (S)')
    plt.ylabel('K_max * Lz')
    plt.legend()

    plt.show()
