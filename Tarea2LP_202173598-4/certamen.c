#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include "certamen.h"


/*
Descripción de la función
Crea un certamen con memoria dinamica con la cantidad de preguntas que se obtiene del archivo

    Parametros:
        n_preguntas (int): entero cantidad de preguntas que tendrá el certamen
    
    Retorno:
        certamen (tCertamen*): retorna un tCertamen* con la cantidad de preguntas ingresadas

*/

tCertamen* crearCertamen(int n_preguntas){  
    tCertamen* certamen = (tCertamen*) malloc(sizeof(tCertamen));
    certamen->n_preguntas = n_preguntas;
    certamen->preguntas = (tPregunta*) malloc(n_preguntas*sizeof(tPregunta));
    return certamen;
}

/*
Descripción de la función
Crea una pregunta con el enunciado, tipo de pregunta y función de revisión correspondientes

    Parametros:
        n_preguntas (int): entero cantidad de preguntas que tendrá el certamen
        tipo (char*): string con el tipo de pregunta que se va a crear
        enunciado (void*): puntero a void que contiene el enunciado de la pregunta de algun tEnunciado segun el tipo de pregunta
        (*fun)(void*,void*) (bool): puntero a la función de revision correspondiente segun el tipo de pregunta
    
    Retorno:
        pregunta (tPregunta*): retorna la pregunta creada con los parametros mencionados anteriormente

*/

tPregunta* crearPregunta(tCertamen* certamen, char* tipo, void* enunciado, bool (*fun)(void*,void*)){

    tPregunta* pregunta = (tPregunta*) malloc (sizeof(tPregunta));
    
    pregunta->enunciado = enunciado;
    strcpy(pregunta->tipo,tipo);
    pregunta->revisar = fun;            
    return pregunta;
    
}

/*
Descripción de la función
Asigna una pregunta a la posicion n_pregunta del certamen

    Parametros:
        certamen (tCertamen*): certamen al que se le asigna la pregunta
        n_pregunta (int): posición del certamen donde irá asignada la pregunta
        pregunta (tPregunta*): pregunta que será asignada en la posición n_pregunta del certamen
    
*/

void asignarPregunta(tCertamen* certamen, int n_pregunta, tPregunta* pregunta){
    certamen->preguntas[n_pregunta] = *pregunta;
    free(pregunta);
}

/*
Descripción de la función
Retorna la pregunta en la posición n_pregunta del certamen

    Parametros:
        certamen (tCertamen*): Certamen que contiene las preguntas
        n_pregunta (int): posición de la pregunta del certamen que se busca retornar
    
    Retorno:
        pregunta (tPregunta): pregunta correspondiente a la posición n_pregunta del certamen

*/

tPregunta leerPregunta(tCertamen* certamen, int n_pregunta){
    return certamen->preguntas[n_pregunta];
}

/*
Descripción de la función
Retorna la cantidad de respuestas correctas que tiene el certamen, haciendo uso de las funciones revisar

    Parametros:
        certamen (tCertamen*): Certamen que contiene las preguntas y respuestas, las cuales seran verificadas con sus correspondientes funciones de revisar segun el tipo
    
    Retorno:
        certamen->preguntas[n_pregunta] (int): cantidad de preguntas correctas del certamen

*/

int nCorrectasCertamen(tCertamen* certamen){
    int cont = 0;
    for (int i = 0; i < certamen->n_preguntas; i++){
        
        if(strcmp(certamen->preguntas[i].tipo,"AlternativaSimple") == 0){

            if (certamen->preguntas[i].revisar(certamen->preguntas[i].enunciado,certamen->preguntas[i].respuesta))
                cont++;

            for (int j = 0; j < (int)((tEnunciadoAlternativa*)certamen->preguntas[i].enunciado)->n_alternativas; j++){
                free(((tEnunciadoAlternativa*)certamen->preguntas[i].enunciado)->alternativas[j]); // free cada alternativa en alternativas simples
            }

            free(((tEnunciadoAlternativa*)certamen->preguntas[i].enunciado)->alternativas);
            free(certamen->preguntas[i].respuesta);   
        }
        else if (strcmp(certamen->preguntas[i].tipo,"AlternativaMultiple") == 0){

            if (certamen->preguntas[i].revisar(certamen->preguntas[i].enunciado,certamen->preguntas[i].respuesta))
                cont++;

            for (int j = 0; j < (int)((tEnunciadoAlternativaMultiple*)certamen->preguntas[i].enunciado)->n_alternativas; j++){
                free(((tEnunciadoAlternativaMultiple*)certamen->preguntas[i].enunciado)->alternativas[j]);
            }   

            free(((tEnunciadoAlternativaMultiple*)certamen->preguntas[i].enunciado)->alternativas);
            free(((tEnunciadoAlternativaMultiple*)certamen->preguntas[i].enunciado)->alternativa_correcta);
            free(certamen->preguntas[i].respuesta);
        }
        else if (strcmp(certamen->preguntas[i].tipo,"VerdaderoFalso") == 0){
            
            if (certamen->preguntas[i].revisar(certamen->preguntas[i].enunciado,certamen->preguntas[i].respuesta))
                cont++;
            
            free(certamen->preguntas[i].respuesta);
        }
        else {            
            if (certamen->preguntas[i].revisar(certamen->preguntas[i].enunciado,certamen->preguntas[i].respuesta))
                cont++; 

            for (int j = 0; j < (int)((tEnunciadoCompletar*)certamen->preguntas[i].enunciado)->n_textos; j++)                
                free(((tEnunciadoCompletar*)certamen->preguntas[i].enunciado)->textos[j]);

            for (int z = 0; z < (int)((tEnunciadoCompletar*)certamen->preguntas[i].enunciado)->n_textos - 1; z++)
                free(((tEnunciadoCompletar*)certamen->preguntas[i].enunciado)->respuestas[z]);

            free(((tEnunciadoCompletar*)certamen->preguntas[i].enunciado)->respuestas);
            free(((tEnunciadoCompletar*)certamen->preguntas[i].enunciado)->textos);
        }
        free(certamen->preguntas[i].enunciado);                
    }
    return cont;
}    

/*
Descripción de la función
Retorna la cantidad de preguntas que tiene el certamen

    Parametros:
        certamen (tCertamen*): Certamen que contiene las preguntas
    
    Retorno:
        certamen->n_preguntas (int): cantidad de preguntas del certamen

*/

int cantPreguntasCertamen(tCertamen* certamen){
    return certamen->n_preguntas;
}

/*
Descripción de la función
En el tipo de pregunta Alternativa Simple, retorna verdadero si la respuestas a la pregunta es correcta y falso si la respuesta a la pregunta es incorrecta

    Parametros:
        enunciado (void*): puntero a void que tiene el enunciado de la pregunta que se desea verificar la respuesta
        respuesta (void*): puntero a void que contiene la respuesta, en este caso, contendrá el numero de la alternativa
    
    Retorno:
        true/false (bool): retorna true si la respuesta es correcta o false si la respuestas es errónea

*/

bool revisarAlternativaSimple(void* enunciado, void* respuesta){
    int correcta = *((int*)respuesta);

    if (((tEnunciadoAlternativa*)enunciado)->alternativa_correcta == correcta)
        return true;   
    
    return false;
}

/*
Descripción de la función
En el tipo de pregunta Alternativa Multiple, retorna verdadero si la respuestas a la pregunta es correcta y falso si la respuesta a la pregunta es incorrecta

    Parametros:
        enunciado (void*): puntero a void que tiene el enunciado de la pregunta que se desea verificar la respuesta
        respuesta (void*): puntero a void que contiene la respuesta, en este caso, contendrá un arreglo de enteros con el numero
                           de las respuestas ingresadas por el usuario
    
    Retorno:
        true/false (bool): retorna true si la respuesta es correcta o false si la respuestas es errónea

*/

bool revisarAlternativaMultiple(void* enunciado, void* respuesta){ // respuestas debe ser int*
    bool resp = true;

    if (((int*)respuesta)[0] != ((tEnunciadoAlternativaMultiple*)enunciado)->n_correctas)
        return false;
    
    for (int i = 0; i < ((tEnunciadoAlternativaMultiple*)enunciado)->n_correctas; i++){
        if (((tEnunciadoAlternativaMultiple*)enunciado)->alternativa_correcta[i] != ((int*)respuesta)[i+1])
                resp = false;    
    }

    return resp;  
} 

/*
Descripción de la función
En el tipo de pregunta Verdadero Falso, retorna verdadero si la respuesta a la pregunta es correcta y falso si la respuesta a la pregunta es incorrecta

    Parametros:
        enunciado (void*): puntero a void que tiene el enunciado de la pregunta que se desea verificar la respuesta
        respuesta (void*): puntero a void que contiene la respuesta, en este caso, contendrá un entero 1 si el usuario ingreso que es verdadero y 0 si cree que es falso
    
    Retorno:
        true/false (bool): retorna true si la respuesta es correcta o false si la respuestas es errónea

*/

bool revisarVerdaderoFalso(void* enunciado, void* respuesta){
    int correcta = *((int*)respuesta);
    
    if (correcta == 1 && ((tEnunciadoVerdaderoFalso*)enunciado)->respuesta == true)
        return true;  
    else
        return false;   
}

/*
Descripción de la función
En el tipo de pregunta Completar, retorna verdadero si la respuestas a la pregunta es correcta y falso si la respuesta a la pregunta es incorrecta

    Parametros:
        enunciado (void*): puntero a void que tiene el enunciado de la pregunta que se desea verificar la respuesta
        respuesta (void*): puntero a void que contiene la respuesta, en este caso, contendrá un arreglo de strings que ingresó el usuario
    
    Retorno:
        true/false (bool): retorna true si la respuesta es correcta o false si la respuestas es errónea

*/

bool revisarCompletar(void* enunciado, void* respuesta){ // respuestas debe ser char**
    bool resp = true;

    for (int i = 0; i < ((tEnunciadoCompletar*)enunciado)->n_textos-1; i++){
        
        ((tEnunciadoCompletar*)enunciado)->respuestas[i][strcspn(((tEnunciadoCompletar*)enunciado)->respuestas[i], "\n")] = 0;
       
        if (strcmp(((tEnunciadoCompletar*)enunciado)->respuestas[i],((char**)respuesta)[i]) != 0){
            resp = false;
        }
        free(((char**)respuesta)[i]);  
    }
    free(respuesta);         
    return resp;
}