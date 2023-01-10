#ifndef CERTAMEN 
#define CERTAMEN
#include <stdlib.h>

typedef struct {
    char enunciado[128];
    int n_alternativas;
    char** alternativas;
    int alternativa_correcta;
} tEnunciadoAlternativa;

typedef struct {
    char enunciado[128];
    int n_alternativas;
    char** alternativas;
    int n_correctas;
    int* alternativa_correcta;
} tEnunciadoAlternativaMultiple;

typedef struct {
    char enunciado[128];
    bool respuesta;
} tEnunciadoVerdaderoFalso;

typedef struct {
    int n_textos;
    char** textos;
    char** respuestas;
} tEnunciadoCompletar;

typedef struct {
    char tipo[64];
    void* enunciado;
    void* respuesta;
    bool (*revisar)(void*,void*); // puntero a alguna de las funciones revisar
} tPregunta;

typedef struct {
    int n_preguntas;
    tPregunta* preguntas;
} tCertamen;

tCertamen* crearCertamen(int n_preguntas);
tPregunta* crearPregunta(tCertamen* certamen, char* tipo, void* enunciado, bool (*fun)(void*,void*));
void asignarPregunta(tCertamen* certamen, int n_pregunta, tPregunta* pregunta);
tPregunta leerPregunta(tCertamen* certamen, int n_pregunta);
int nCorrectasCertamen(tCertamen* certamen);
int cantPreguntasCertamen(tCertamen* certamen);
bool revisarAlternativaSimple(void* enunciado, void* respuesta);
bool revisarAlternativaMultiple(void* enunciado, void* respuesta); 
bool revisarVerdaderoFalso(void* enunciado, void* respuesta); 
bool revisarCompletar(void* enunciado, void* respuesta);


#endif