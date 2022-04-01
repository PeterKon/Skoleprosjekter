#!/bin/sh

import numpy as np
from timeit import default_timer as timer
from matplotlib import pyplot as plt
from matplotlib.colors import LinearSegmentedColormap

""" Funksjonen tar inn verdiene xmin, xmax, ymin, ymax som setter verdien
    til omraadet vi oensker aa undersoeke. Det sendes ogsaa med en bredde-
    hoyde variabel som bestemmer bildets form/opploesning. Funksjonen danner
    et array c med komplekse tall for alle verdier width * height og en maske
    for aa sjekke hvilke punkter som har konvergert. Saa gaar den opp til
    maxiter og bruker numpy-array operasjoner for aa oppdatere n3 basert paa om
    punktene har konvergert eller ikke. Masken gjoer at kun punkter som ikke
    har konvergert faar utfoert z^2 + c -operasjonen.

    Returnerer -> To-dimensjonellt array n3 som inneholder escape values
    for alle piksler
"""
def mandelbrot_set(xmin,xmax,ymin,ymax,width,height,maxiter):

    r1 = np.linspace(xmin, xmax, width)
    r2 = np.linspace(ymin, ymax, height)
    n3 = np.zeros((height, width))

    r2_vert = r2.reshape(height,1)

    #Arrayet r1 staar for de reelle delene av de komplekse tallene, og
    #r2 staar for de imaginere delene. Vi oensker fra disse aa konstruere
    #ett to-dimensjonellt array med komplekse tall som har sin venstreside
    #(reell del) av delene i r1 mot r2 (imaginer del). Vi har et reshapet
    #vertikalt r2 og kan derfor med foelgende linje konstruere et nytt array
    #"c" i 2D som vil inneholde alle de komplekse tallene vi skal bruke.
    c = r1 + 1j * r2_vert

    #Boolean mask for aa unnga overflow ved sjekk paa plasser som
    #Allerede har konvergert
    mask = np.ones((height, width), dtype=np.bool_)

    #Vi danner et 2D array med komplekse tall. I foerste iterasjon er dette
    #arrayet kun bestaende av null og blir derfor satt til aa inneholde det
    #Samme som c inne i loekken. Deretter vil det inneholde produktet av
    #seg selv gange seg selv pluss c respektivt til alle plasser, z*z + c
    z = np.zeros((height, width), dtype=np.complex64)
    for n in range(maxiter):

        #Paa n3 = n3 + (abs(z) < 2.0) er det som skjer at alle posisjoner der
        #absoluttverdien av z er stoerre enn 2 itereres med en, og de som er
        #i mandelbrot gaar derfor opp mot maxiter stoerrelse. Vi bruker mask
        #for aa unngaa overflow error. Mask oppdateres etter hver iterasjon
        #for aa sjekke hva som har konvergert
        z[mask] = z[mask]*z[mask] + c[mask]
        n3 = n3 + (abs(z) < 2.0)
        mask = (abs(z) < 2.0)

    return (n3)

def mandelbrot_image(xmin,xmax,ymin,ymax,width,height,maxiter,fname,color_choice):

    print()
    print("-- Timing 3 runs ---")

    start1 = timer()
    z1 = mandelbrot_set(xmin,xmax,ymin,ymax,width,height,maxiter)
    stop1 = timer()

    start2 = timer()
    z2 = mandelbrot_set(xmin,xmax,ymin,ymax,width,height,maxiter)
    stop2 = timer()

    start3 = timer()
    z3 = mandelbrot_set(xmin,xmax,ymin,ymax,width,height,maxiter)
    stop3 = timer()

    print()
    print("Results:")
    print(stop1 - start1)
    print(stop2 - start2)
    print(stop3 - start3)
    print()

    #Med denne sammenstillingen i "colors" saa vil alltid de fargene (CHOICE = 1) som har
    #"maxiter" verdi tilegnes fargen svart gitt av (0, 0, 0), og de andre
    #fargene vil farges etter hvilken maxiter verdi de fikk naar de konvergerte,
    #dvs deres verdi fra 1 til maxiter. Dette fungerer fordi vaart 2D array
    #som returneres inneholder alle maxiter verdiene for konvergering.
    if(fname != None):
        if(color_choice == "1"):
            cmap_name = "Liste mandelbrot"
            n_bins = np.arange(maxiter)
            colors = [(1, 0, 0), (0, 1, 0), (0, 0, 1), (0, 0, 0)]
            cma = LinearSegmentedColormap.from_list(cmap_name, colors, maxiter)

            plt.imshow(z1, cmap=cma)
            plt.savefig(fname)
            plt.show()
        if(color_choice == "2"):
            cmap_name = "Liste mandelbrot"
            n_bins = np.arange(maxiter)
            colors = [(1, 1, 1), (1, 1, 0), (0, 0, 1), (0, 1, 0)]
            cma = LinearSegmentedColormap.from_list(cmap_name, colors, maxiter)

            plt.imshow(z1, cmap=cma)
            plt.savefig(fname)
            plt.show()
        if(color_choice == "3"):
            cmap_name = "Liste mandelbrot"
            n_bins = np.arange(maxiter)
            colors = [(0, 0, 0), (0, 1, 0), (0, 0, 1), (0, 1, 0)]
            cma = LinearSegmentedColormap.from_list(cmap_name, colors, maxiter)

            plt.imshow(z1, cmap=cma)
            plt.savefig(fname)
            plt.show()
    else:
        if(color_choice == "1"):
            cmap_name = "Liste mandelbrot"
            n_bins = np.arange(maxiter)
            colors = [(1, 0, 0), (0, 1, 0), (0, 0, 1), (0, 0, 0)]
            cma = LinearSegmentedColormap.from_list(cmap_name, colors, maxiter)

            plt.imshow(z1, cmap=cma)
            plt.show()
        if(color_choice == "2"):
            cmap_name = "Liste mandelbrot"
            n_bins = np.arange(maxiter)
            colors = [(1, 1, 1), (1, 1, 0), (0, 0, 1), (0, 1, 0)]
            cma = LinearSegmentedColormap.from_list(cmap_name, colors, maxiter)

            plt.imshow(z1, cmap=cma)
            plt.show()
        if(color_choice == "3"):
            cmap_name = "Liste mandelbrot"
            n_bins = np.arange(maxiter)
            colors = [(0, 0, 0), (0, 1, 0), (0, 0, 1), (0, 1, 0)]
            cma = LinearSegmentedColormap.from_list(cmap_name, colors, maxiter)

            plt.imshow(z1, cmap=cma)
            plt.show()


mandelbrot_image(-2.0,0.5,-1.25,1.25,500,500,50)
