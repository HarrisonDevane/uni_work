current = [0 0.2 0.4 0.6 0.8 1.0 1.2];
magnetic = [15 38.4 70.4 100.8 136.5 175.0 207.9] * 10^-3;
current_err = [0.05 0.05 0.05 0.05 0.05 0.05 0.05];
magnetic_err = [0.1 0.1 0.1 0.1 0.1 0.1 0.1] * 10^-3;

xlabel = "Current (A)";
ylabel = "Magnetic Field Strength (T)";
errorbar(current, magnetic, magnetic_err, magnetic_err, current_err, current_err, '*'), 

%Part A

%Vh = [19.68 21.03 22.32 23.79 25.15 26.53 27.71]*10^-3 %average
%d = 0.4e-3 %mm thickness of probe
%I = 1e-3 %mV

%plot(magnetic,Vh, '*')

%Part B

Vh = [3.30 8.28 13.76 18.9 23.95 29.16 34] * 10^-3;
plot(magnetic, Vh, '*');