import numpy as np # pip install numpy
from PIL import Image # pip install Pllow
import re

'''
def MatrizAImagen()
Descripción de la función

Convierte una matriz de valores RGB en una imagen y la guarda como un archivo png.
Las imagenes son escaladas por un factor ya que con los ejemplos se producirian imagenes muy pequeñas.
    Parametros:
            matriz (lista de lista de tuplas de enteros): Matriz que representa la imagen en rgb.
            filename (str): Nombre del archivo en que se guardara la imagen.
            factor (int): Factor por el cual se escala el tamaño de las imagenes.
'''
def MatrizAImagen(matriz, filename='pixelart.png', factor=10):
    matriz = np.array(matriz, dtype=np.uint8)
    np.swapaxes(matriz, 0, -1)

    N = np.shape(matriz)[0]

    img = Image.fromarray(matriz, 'RGB')
    img = img.resize((N*10, N*10), Image.Resampling.BOX)
    img.save(filename)

'''
def instruccionesRepetidas()
Descripción de la función

Funcion que se utiliza para realizar las instrucciones adentro de las llaves de una instruccion Repetir 
de manera recursiva, de forma similar a la que se encuentra en el codigo principal
    
    Parametros:
            instrucciones (str) : String con las instrucciones que se encuentran adentro del Repetir en el archivo
            numero (str) : String de un numero, el cual en la funcion es pasada a int, el cual es la cantidad de veces 
                           que se repiten las instrucciones
            cont (int): Entero que sirve para hacer las iteraciones del ciclo While, comparandolas con la variable numero.
'''
def instruccionesRepetidas(instrucciones,numero,cont):
    global norte, sur, este, oeste, fila, columna, matriz, ancho

    contLlaveAnidada = 0

    while cont < numero:
        instruccionesAnidada = instrucciones
        while len(instruccionesAnidada) > 0:

            if fila == int(ancho) or columna == int(ancho) or fila < 0 or columna < 0:
                print("Está intentando trabajar fuera de la matriz")
                quit()

            if re.match(avanzar,instruccionesAnidada) != None: # Avanzar N
                match = re.match(avanzar,instruccionesAnidada)
                if match.group(1) != None: # hizo match con un numero N
                    N = match.group(1)
                    if este == True:
                        columna += int(N)
                    elif sur == True:
                        fila += int(N)
                    elif oeste == True:
                        columna -= int(N)
                    elif norte == True:
                        fila -= int(N)
                else: # No existe el numero N, avanza 1
                    if este == True:
                        columna += 1
                    elif sur == True:
                        fila += 1
                    elif oeste == True:
                        columna -= 1
                    elif norte == True:
                        fila -= 1
            
            elif re.match(giros,instruccionesAnidada) != None: # Derecha | Izquierda
                match = re.match(giros,instruccionesAnidada)

                if re.match(r'Derecha',match.group()): # Match con Derecha
                    if este == True:
                        sur = True
                        este = False
                    elif sur == True:
                        oeste = True
                        sur = False
                    elif oeste == True:
                        norte = True
                        oeste = False
                    elif norte == True:
                        este = True
                        norte = False
                else: # Match con Izquierda
                    if este == True:
                        norte = True
                        este = False
                    elif sur == True:
                        este = True
                        sur = False
                    elif oeste == True:
                        sur = True
                        oeste = False
                    elif norte == True:
                        oeste = True
                        norte = False

            elif re.match(pintar,instruccionesAnidada) != None: # Pintar
                match = re.match(pintar,instruccionesAnidada)

                if match.group(3) != None: # Pintar RGB()
                    colorAPintar = match.group(3)
                    matriz[fila][columna] = eval(colorAPintar)
        
                else: # Pintar con el color correspondiente
                    colorAPintar = match.group(2)
                    if re.match(r'Rojo',colorAPintar) != None:
                        matriz[fila][columna] = (255,0,0)
                    elif re.match(r'Verde',colorAPintar) != None:
                        matriz[fila][columna] = (0,255,0)
                    elif re.match(r'Azul',colorAPintar) != None:
                        matriz[fila][columna] = (0,0,255)
                    elif re.match(r'Negro',colorAPintar) != None:
                        matriz[fila][columna] = (0,0,0)
                    elif re.match(r'Blanco',colorAPintar) != None:
                        matriz[fila][columna] = (255,255,255)

            elif re.match(repiteLinea,instruccionesAnidada) != None: 
                match = re.match(repiteLinea,instruccionesAnidada) 
                numRep = int(match.group(2))

                j = 0
                for letra in instruccionesAnidada:
                    if letra == "{":
                        contLlaveAnidada += 1
                        if contLlaveAnidada == 0:
                            break                    
                    elif letra == "}":
                        contLlaveAnidada -= 1
                        if contLlaveAnidada == 0:
                            break              
                    j += 1

                matchInicio = re.search("{",instruccionesAnidada)
                ins = instruccionesAnidada[matchInicio.end()+1:j]
                a = 0
                instruccionesRepetidas(ins,numRep-1,a)
           
            elif re.match(repiteLineaFin,instruccionesAnidada) != None:
                match = re.match(repiteLineaFin,instruccionesAnidada)
            
            instruccionesAnidada = instruccionesAnidada[match.end():]
        
        cont += 1


# Expresiones Regulares
anch = re.compile(r'(^Ancho (\d+))$')
fondo = re.compile(r'^(Color de fondo (Rojo|Verde|Azul|Negro|Blanco|RGB\(\b([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\b,\b([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\b,\b([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\b\)) ?)')
pintar = re.compile(r'(Pintar (Rojo|Verde|Negro|Azul|Blanco|RGB(\(\b([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\b,\b([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\b,\b([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\b\)))($|\s))')
giros = re.compile(r'(Izquierda|Derecha)(\s|(?!.+))')
avanzar = re.compile(r'Avanzar( \d+)?($|\s)')
repiteLinea = re.compile(r'(Repetir (\d+) veces { ?($|\s))')
repiteLineaFin = re.compile(r'} ?')
rgb = re.compile(r'RGB(\(\b([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\b,\b([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\b,\b([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\b\))')


arch = open('codigo.txt','r',encoding='utf8')
out = open('errores.txt','w',encoding='utf8')
error = False
instrucciones = ""
errorRepetir = [] # Lista que sirve para ir viendo errores en el equilibrio de {}
errores = [] # Lista donde se guardan los errores aparte de los de la lista errorRepetir

#Ciclo para verificar sintaxis del archivo
for i,linea in enumerate(arch):
    linea = linea.strip()
    errorFlag = False
    stringLinea = linea.strip()

    if i > 2:
        instrucciones += linea.strip() + " "
    
    while len(linea) > 0:

        if i > 2:   # Cuarta linea en adelante
            if re.match(avanzar,linea) != None:
                match = re.match(avanzar,linea)
                linea = linea[match.end():]

            elif re.match(giros,linea) != None:
                match = re.match(giros,linea)
                linea = linea[match.end():]

            elif re.match(pintar,linea) != None:
                match = re.match(pintar,linea)
                linea = linea[match.end():]

            elif re.match(repiteLinea,linea) != None:
                match = re.match(repiteLinea,linea)
                linea = linea[match.end():]
                errorRepetir.append([i+1,stringLinea,1])

            elif re.match(repiteLineaFin,linea) != None:
                match = re.match(repiteLineaFin,linea) 

                if len(errorRepetir) == 0: # If para verificar si hay una llave } primero que una {
                    errorFlag = True
                    linea = ""
                else:
                    linea = linea[match.end():]
                    errorRepetir[-1][2] -= 1
                    if errorRepetir[-1][2] == 0:
                        errorRepetir.pop()
            else:
                errorFlag = True
                linea = ""

        else:  # Primera y segunda linea
            if re.match(anch,linea) != None:
                match = re.match(anch,linea)
                linea = linea[match.end():]
                ancho = match.group(2)
            elif re.match(fondo,linea) != None:
                match = re.match(fondo,linea)
                linea = linea[match.end():]
                if re.match(rgb,match.group(2)) != None:
                    color = re.match(rgb,match.group(2))
                    color = color.group(1)
                else:
                    color = match.group(2)
            else:
                errorFlag = True
                linea = ""
        
    if errorFlag == True:
        errores.append([i+1,str(stringLinea)])

erroresFinales = errorRepetir + errores
erroresFinales.sort()

for error in erroresFinales: 
    out.write(str(error[0])+" "+str(error[1])+"\n")

if len(erroresFinales) == 0:

    out.write("No hay errores!")

    # Proceso para crear la matriz con el fondo del color de la segunda linea
    if re.match(r'Rojo',color) != None:
        color = '(255,0,0)'
    elif re.match(r'Verde',color) != None:
        color = '(0,255,0)'
    elif re.match(r'Azul',color) != None:
        color = '(0,0,255)'
    elif re.match(r'Negro',color) != None:
        color = '(0,0,0)'
    elif re.match(r'Blanco',color) != None:
        color = '(255,255,255)'

    # Se inicia la matriz con el color de fondo y el ancho
    matriz = []
    for i in range(int(ancho)):
        fila = [] 
        for j in range(int(ancho)): 
            fila.append(eval(color)) 
        matriz.append(fila) 

    #Ejecutar instrucciones
    fila = columna = contLlave = 0
    norte = sur = oeste = False
    este = True

    while len(instrucciones) > 0:

        if fila == int(ancho) or columna == int(ancho) or fila < 0 or columna < 0:
            print("Está intentando realizar instrucciones fuera de la matriz")
            quit()

        if re.match(avanzar,instrucciones) != None: # Avanzar N
            match = re.match(avanzar,instrucciones)

            if match.group(1) != None: # hizo match con un numero N
                N = match.group(1)
                if este == True:
                    columna += int(N)
                elif sur == True:
                    fila += int(N)
                elif oeste == True:
                    columna -= int(N)
                elif norte == True:
                    fila -= int(N)
            else: # No existe el numero N, avanza 1
                if este == True:
                    columna += 1
                elif sur == True:
                    fila += 1
                elif oeste == True:
                    columna -= 1
                elif norte == True:
                    fila -= 1
                
        elif re.match(giros,instrucciones) != None: # Derecha | Izquierda
            match = re.match(giros,instrucciones)

            if re.match(r'Derecha',match.group()): # Match con Derecha
                if este == True:
                    sur = True
                    este = False
                elif sur == True:
                    oeste = True
                    sur = False
                elif oeste == True:
                    norte = True
                    oeste = False
                elif norte == True:
                    este = True
                    norte = False
            else: # Match con Izquierda
                if este == True:
                    norte = True
                    este = False
                elif sur == True:
                    este = True
                    sur = False
                elif oeste == True:
                    sur = True
                    oeste = False
                elif norte == True:
                    oeste = True
                    norte = False

        elif re.match(pintar,instrucciones) != None: # Pintar
            match = re.match(pintar,instrucciones)

            if match.group(3) != None: # Pintar RGB()
                colorAPintar = match.group(3)
                matriz[fila][columna] = eval(colorAPintar)
    
            else: # Pintar con el color correspondiente
                colorAPintar = match.group(2)
                if re.match(r'Rojo',colorAPintar) != None:
                    matriz[fila][columna] = (255,0,0)
                elif re.match(r'Verde',colorAPintar) != None:
                    matriz[fila][columna] = (0,255,0)
                elif re.match(r'Azul',colorAPintar) != None:
                    matriz[fila][columna] = (0,0,255)
                elif re.match(r'Negro',colorAPintar) != None:
                    matriz[fila][columna] = (0,0,0)
                elif re.match(r'Blanco',colorAPintar) != None:
                    matriz[fila][columna] = (255,255,255)

        elif re.match(repiteLinea,instrucciones) != None: # Instruccion Repetir 4 veces {
            match = re.match(repiteLinea,instrucciones) # Actualizar lista
            numRep = int(match.group(2))

            j = 0
            for letra in instrucciones:
                if re.match(r'{',letra) != None:
                    contLlave += 1
                    if contLlave == 0:
                        break                    
                elif re.match(r'}',letra) != None:
                    contLlave -= 1
                    if contLlave == 0:
                        break              
                j += 1

            matchInicio = re.search("{",instrucciones)
            ins = instrucciones[matchInicio.end()+1:j]
            a = 0
            instruccionesRepetidas(ins,numRep-1,a)
        
        elif re.match(repiteLineaFin,instrucciones) != None:
            match = re.match(repiteLineaFin,instrucciones)
        
        instrucciones = instrucciones[match.end():]           

    MatrizAImagen(matriz)

arch.close()
out.close()