#include <stdio.h>
#include <omp.h>
const int size = 3;

int main()

{

float a[size][size];
float b[size][size];
float c[size][size];

    for (int i = 0; i < size; ++i) {
        for (int j = 0; j < size; ++j) {
            a[i][j] = (float)i + j;
            b[i][j] = (float)i - j;
            c[i][j] = 0.0f;
        }
    }

    #pragma omp parallel for shared(a,b,c)
    for (int i = 0; i < size; ++i) {
        for (int j = 0; j < size; ++j) {
            for (int k = 0; k < size; ++k) {
                c[i][j] += a[i][k] * b[k][j];
            }
        }
    }
    for(int i=0;i<size;i++)
    {
    	for(int j=0;j<size;j++)
    		printf("%f ",c[i][j]);
    	printf("\n");
    }

    return 0;
}