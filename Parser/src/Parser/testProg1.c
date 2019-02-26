    int rows = 5, coef = 1, space, i, j, numSpaces;

    for(i=0; i<rows; i+=1)
    {
        for(space=1; space <= rows-i; space+=1)
            numSpaces++;

        for(j=0; j <= i; j+=1)
        {
            if (j==0 || i==0)
                coef = 1;
            else
                coef = coef*((i-j+1)/j);
        }
    }
