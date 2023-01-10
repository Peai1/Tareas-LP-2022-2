#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include "certamen.h"

int main() {
    FILE *fp;
    char tipo[128];
    tCertamen* certamen;
    void* enun;
    bool (*fun)(void*,void*);
    int cantPreguntas, i , num, cont = 0, contMultiple = 0, contCompletar = 0, contador = 0;
    bool altSimple = false, altMult = false, VerdFal = false, Comp = false;
    fp = fopen("certamen.txt","r");
    fscanf(fp,"%d",&cantPreguntas);

    certamen = crearCertamen(cantPreguntas);
        
    while (fgets(tipo,128,fp) != NULL){
        if (strcmp(tipo,"AlternativaSimple\n") == 0){
            altSimple = true;
            altMult = VerdFal = Comp = false;
            i = 1;
            cont = 0; 
        }       
        else if (strcmp(tipo,"AlternativaMultiple\n") == 0){
            altMult = true;
            altSimple = VerdFal = Comp = false;
            i = 1;
            cont = 0;
            contMultiple = 0;
        }        
        else if (strcmp(tipo,"VerdaderoFalso\n") == 0){
            VerdFal = true;        
            altSimple = altMult = Comp = false;
            i = 1;
        }    
        else if (strcmp(tipo,"Completar\n") == 0){
            Comp = true;
            altSimple = altMult = VerdFal = false;
            i = 1;
            cont = 0;
            contCompletar = 0;
        } 

        else if (altSimple == true){
            fun = revisarAlternativaSimple;
            if (i == 1){ // Enunciado               
                enun = (tEnunciadoAlternativa*) malloc(sizeof(tEnunciadoAlternativa));
                strcpy(((tEnunciadoAlternativa*)enun)->enunciado,tipo);
            }
            else if (i == 2){ // Numero alternativas                
                num = atoi(tipo);
                ((tEnunciadoAlternativa*)enun)->alternativas = (char**)malloc(num*sizeof(char*));
                ((tEnunciadoAlternativa*)enun)->n_alternativas = num;

            }
            else if (cont < num){ // Alternativas  
                int a = strlen(tipo);
                ((tEnunciadoAlternativa*)enun)->alternativas[cont] = malloc(a*sizeof(char*));
                strcpy(((tEnunciadoAlternativa*)enun)->alternativas[cont],tipo);
                cont++;
            }
            else { // La correcta                              
                ((tEnunciadoAlternativa*)enun)->alternativa_correcta = atoi(tipo);
                asignarPregunta(certamen,contador,crearPregunta(certamen,"AlternativaSimple",enun,fun));
                contador++;
            }
            i++;
        }
        else if (altMult == true){
            fun = revisarAlternativaMultiple;
            if (i == 1){ // Enunciado
                enun = (tEnunciadoAlternativaMultiple*) malloc(sizeof(tEnunciadoAlternativaMultiple));
                strcpy(((tEnunciadoAlternativaMultiple*)enun)->enunciado,tipo);
            }
            else if (i == 2){ // Numero de alternativas
                num = atoi(tipo);           
                ((tEnunciadoAlternativaMultiple*)enun)->alternativas = (char**)malloc(num*sizeof(char*));
                ((tEnunciadoAlternativaMultiple*)enun)->n_alternativas = num;
            }
            else if (cont < num){ // Alternativas
                ((tEnunciadoAlternativaMultiple*)enun)->alternativas[cont] = malloc(strlen(tipo)*sizeof(char*));
                strcpy(((tEnunciadoAlternativaMultiple*)enun)->alternativas[cont],tipo);
                cont++;
            }
            else if (i == num + 3){ // Cantidad de correctas 
                ((tEnunciadoAlternativaMultiple*)enun)->n_correctas = atoi(tipo);
                ((tEnunciadoAlternativaMultiple*)enun)->alternativa_correcta = (int*) malloc(atoi(tipo)*sizeof(int));
            }
            else { // Alternativas correctas
                ((tEnunciadoAlternativaMultiple*)enun)->alternativa_correcta[contMultiple] = atoi(tipo);
                contMultiple++;
                if (contMultiple == (int)(((tEnunciadoAlternativaMultiple*)enun)->n_correctas) - 1){
                    asignarPregunta(certamen,contador,crearPregunta(certamen,"AlternativaMultiple",enun,fun));
                    contador++;
                }
            }
            i++;
        }
        else if (VerdFal == true){
            fun = revisarVerdaderoFalso;
            if (i == 1){ // Enunciado               
                enun = (tEnunciadoVerdaderoFalso*) malloc(sizeof(tEnunciadoVerdaderoFalso));
                strcpy(((tEnunciadoVerdaderoFalso*)enun)->enunciado,tipo);
            }
            else // Verdadero o Falso                             
                if (strcmp(tipo,"V") == 0 || strcmp(tipo,"V\n") == 0){                    
                    ((tEnunciadoVerdaderoFalso*)enun)->respuesta = true;
                    asignarPregunta(certamen,contador,crearPregunta(certamen,"VerdaderoFalso",enun,fun));
                    contador++;
              
                }                    
                else{
                    ((tEnunciadoVerdaderoFalso*)enun)->respuesta = false;     
                    asignarPregunta(certamen,contador,crearPregunta(certamen,"VerdaderoFalso",enun,fun));
                    contador++;

                }
            i++;           
        }       
        else if (Comp == true){
            fun = revisarCompletar;
            if (i == 1){ 
                num = atoi(tipo);
                enun = (tEnunciadoCompletar*) malloc(sizeof(tEnunciadoCompletar));
                ((tEnunciadoCompletar*)enun)->n_textos = num;
                ((tEnunciadoCompletar*)enun)->textos = (char**) malloc(num*sizeof(char*));
                int a = num - 1;
                ((tEnunciadoCompletar*)enun)->respuestas = (char**) malloc(a*sizeof(char*));

            }
            else if (cont < num){
                int b = strlen(tipo);             
                ((tEnunciadoCompletar*)enun)->textos[cont] = malloc(b*sizeof(char*));
                strcpy(((tEnunciadoCompletar*)enun)->textos[cont],tipo);
                cont++;
            }
            else if (contCompletar < num-1) {
                int c = strlen(tipo);
                ((tEnunciadoCompletar*)enun)->respuestas[contCompletar] = malloc(c*sizeof(char*));
                strcpy(((tEnunciadoCompletar*)enun)->respuestas[contCompletar],tipo);
                contCompletar++;

                if (contCompletar == num-1){
                    asignarPregunta(certamen,contador,crearPregunta(certamen,"Completar",enun,fun));
                    contador++;
                }                
            }           
            i++;        
        }
    }

    for (int i = 0; i < cantPreguntasCertamen(certamen); i++){
        printf("--------------------------\n");

        tPregunta preg = leerPregunta(certamen,i);

        if (strcmp((char*)preg.tipo,"AlternativaSimple") == 0){

            int correcta;

            printf("Pregunta numero %d: Alternativa Simple\n",i+1);
            printf("%s",(char*)((tEnunciadoAlternativa*)preg.enunciado)->enunciado);

            for (int j = 0; j < (int)((tEnunciadoAlternativa*)preg.enunciado)->n_alternativas; j++)
                printf("%d) %s",j+1,(char*)((tEnunciadoAlternativa*)preg.enunciado)->alternativas[j]);
               
            printf("Ingrese el numero de la alternativa correcta: ");
            scanf("%d",&correcta);
            getchar();

            certamen->preguntas[i].respuesta = malloc(sizeof(int));

            *(int*)certamen->preguntas[i].respuesta = correcta;
                   
        }

        else if ((strcmp((char*)preg.tipo,"AlternativaMultiple") == 0)){
            
            int tam;
            printf("Pregunta numero %d: Alternativa Multiple\n",i+1);
            printf("%s",(char*)((tEnunciadoAlternativaMultiple*)preg.enunciado)->enunciado);

            for (int j = 0; j < (int)((tEnunciadoAlternativaMultiple*)preg.enunciado)->n_alternativas; j++)
                printf("%d) %s",j+1,(char*)((tEnunciadoAlternativaMultiple*)preg.enunciado)->alternativas[j]);
                            
            printf("Ingrese la cantidad de alternativas que cree son correctas: ");
            scanf("%d",&tam);
            getchar();

            int a = tam + 1;
            int *correctas = malloc(a * sizeof(int));

            correctas[0] = tam;

            printf("Ingrese el numero correspondiente a las alternativas correctas una por una (en orden de menor a mayor) \n");
            for (int j = 1; j <= tam; j++){
                scanf("%d",&correctas[j]);
                getchar();
            }
            
            certamen->preguntas[i].respuesta = correctas;
         
        }

        else if ((strcmp((char*)preg.tipo,"VerdaderoFalso") == 0)){

            int bol;
            printf("Pregunta numero %d: Verdadero/Falso\n",i+1);
            printf("%s",(char*)((tEnunciadoVerdaderoFalso*)preg.enunciado)->enunciado);            
            printf("Ingrese 1 si el enunciado es verdadero o 0 si es falso: ");
            scanf("%d",&bol);
            getchar();

            certamen->preguntas[i].respuesta = malloc(sizeof(int));

            *(int*)certamen->preguntas[i].respuesta = bol;

        }

        else if ((strcmp((char*)preg.tipo,"Completar") == 0)){
            
            printf("Pregunta numero %d: Completar \n",i+1);
            printf("Complete los siguientes enunciados que se encuentran separados por linea \n");
            
            int a = (((tEnunciadoCompletar*)preg.enunciado)->n_textos) - 1;
            char** strings = (char**) malloc(a * sizeof(char*));
            char ingresado[128];
            int largo = 0;

            for (int j = 0; j < (int)((tEnunciadoCompletar*)preg.enunciado)->n_textos; j++)
                printf("%d) %s",j+1,(char*)((tEnunciadoCompletar*)preg.enunciado)->textos[j]);         

            printf("Alternativas:\n");

            for (int z = 0; z < (int)((tEnunciadoCompletar*)preg.enunciado)->n_textos - 1; z++){
                printf("Ingrese la palabra que va entre el enunciado %d y %d: ",z+1,z+2);
                fgets(ingresado,sizeof(ingresado),stdin);
                largo = strlen(ingresado);
                ingresado[largo-1] = '\0';
                strings[z] = malloc(largo*sizeof(char));
                strcpy(strings[z],ingresado);
            }            
            certamen->preguntas[i].respuesta = strings;           
        }       
        printf("\n--------------------------");
    }

    printf("\nCantidad de correctas: %d/%d\n",nCorrectasCertamen(certamen),cantPreguntasCertamen(certamen));
    printf("Gracias por responder :)");
    free(certamen->preguntas);
    free(certamen);
   
    printf("\n"); 
    fclose(fp);
    return 0;
}