#include<stdio.h>
#include<omp.h>
void main()
{
    int x=0;
    int id=222;
    #pragma omp parallel num_threads(3)
    {
        #pragma omp sections 
        {    
            #pragma omp section
            { 
                id=omp_get_thread_num();
                printf ("id = %d A, \n", omp_get_thread_num());
            }
            #pragma omp section
            { 
                id=omp_get_thread_num();
                printf ("id = %d B, \n", omp_get_thread_num());
            }
            #pragma omp section
            { 
                // id=omp_get_thread_num();
                printf ("id = %d C, \n", omp_get_thread_num());
            }
        }
    }
    printf("id : %d tid:%d\n",id,omp_get_thread_num());

}