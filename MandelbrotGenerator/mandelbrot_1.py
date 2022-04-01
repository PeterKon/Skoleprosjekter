#!/bin/sh

import numpy as np
from timeit import default_timer as timer
from matplotlib import pyplot as plt
from matplotlib.colors import LinearSegmentedColormap

""" Funksjonen tar inn verdiene xmin, xmax, ymin, ymax som setter verdien
    til omraadet vi oensker aa undersoeke. Det sendes ogsaa med en bredde-
    hoyde variabel som bestemmer bildets form/opploesning. Funksjonen itererer
    gjennom hoyde og bredde og konstruerer komplekse tall basert paa hvor
    i feltet det skal dannes en piksel. Saa blir hver piksel satt ved at
    en itererer opp til maks maxiter og sjekker om absoluttverdi er stoerre
    enn 2, om ja saa vil pikselen faa verdi v for hvor mange iterasjoner den
    brukte.

    Returnerer -> To-dimensjonellt array n3 som inneholder escape values
    for alle piksler
"""
def mandelbrot_set(xmin,xmax,ymin,ymax,width,height,maxiter):

    #Vi lager r1 og r2, hvor r1 gir den reelle delen til alle komplekse
    #tall, og r2 den imaginere. I selve funksjonen kan vi iterere over
    #Disse arrayene
    r1 = np.linspace(xmin, xmax, width)
    r2 = np.linspace(ymin, ymax, height)
    n3 = np.zeros((height, width))

    #set gis for aa spore naar noe har konvergert. Paa slutten settes
    #indeksen paa denne plassen til maxiter dersom ikke. Vi konstruerer
    #komplekse tall ut fra r1 og r2, og sjekker om absoluttverdien til
    #z er stoerre enn to, dersom ja har den konvergert og pikselverdien
    #her blir n.
    for w in range(width):
        for t in range(height):

            set = False
            c = complex(r1[w], r2[t])
            z = complex(r1[w], r2[t])
            for n in range(maxiter):
                if abs(z) > 2:
                    n3[t][w] = n
                    set = True
                    break
                z = z*z + c
            if set:
                continue
            else:
                n3[t][w] = maxiter

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


#mandelbrot_image(-2.0,0.5,-1.25,1.25,500,500,50)
