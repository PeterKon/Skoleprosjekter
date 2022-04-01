#include <stdio.h>
#include <string.h>
#include <stdlib.h>

/*
I Struct router tar vi vare paa en streng output og
tre unsigned chars.
*/
struct router {
     char output[253];
     unsigned char rID, flag, len;
};

//Statisk globalt array med pekere pa routere
struct router *map[255];

/*
Denne funksjonen tar inn en peker til en router-struct og
printer ut alle medlemmene. Denne brukes mest for test-formaal
og finnes nede i koden om du vil fjaerne kallet paa den. Den
printer ogsaa status paa funksjonene
*/
void print_router(struct router *rt) {
    printf( "ID: %hhu\n", rt->rID);
    printf( "FLAG: %hhu\n", rt->flag);
    printf( "LEN: %hhu\n", rt->len);

    if(is_nth_set(rt, 0)) {
        printf("Denne routeren er AKTIV\n");
    } else {
        printf("Denne routeren er IKKE AKTIV\n");
    }
    if(is_nth_set(rt, 1)) {
        printf("Denne routeren er TRAADLOOS\n");
    } else {
        printf("Denne routeren er IKKE TRAADLOOS\n");
    }
    if(is_nth_set(rt, 2)) {
        printf("Denne routeren STOTTER 5ghz\n");
    } else {
        printf("Denne routeren STOTTER IKKE 5ghz\n");
    }

    printf("%s\n", rt->output);

}

/*
Naar vi sletter en router-struct maa vi frigjoere minnet paa denne
posisjonen ogsaa nulle ut det som var der.
*/
void delete_router(int index) {
    free(map[index]);
    map[index] = NULL;
}

/*
Rotete metode, men den setter bit paa posisjon ind til aa
vaere lik biten du sender med, inspirasjon fra stack
*/
void router_set_bit(struct router*rt, int ind, int bit) {
    rt->flag = (rt->flag & (~(1 << ind))) | (bit << ind);
}

/*
Sjekker om bit paa posisjon n er satt, inspirasjon fra stack
*/
int is_nth_set(struct router *rt, int n) {
    static unsigned char mask[] = {128, 64, 32, 16, 8, 4, 2, 1};
    return ((rt->flag & mask[n]) != 0);
}

void router_set_flag(struct router *rt, unsigned char flag) {
    rt->flag = flag;
}

//Strcpy maa brukes for aa kopiere strengen fra char-arrayet vi mottar
void router_set_output(struct router *rt, char output[]) {
    strcpy(rt->output, output);
    rt->len = strlen(output);
}

void print_meny() {
    printf("\nVelkommen til router-manager, vennligst velg alternativ:\n");
    printf("1: Print info om en router\n");
    printf("2: Slett en router\n");
    printf("3: Endre flagg for router\n");
    printf("4: Endre produsent/modell for router\n");
    printf("5: Legg inn ny router\n");
    printf("6: AVSLUTT\n");
}

int main(int argc, char *argv[]) {

    //Usage-test for aa sjekke korrekt antall argumenter
    if(argc < 2 || argc > 2) {
        printf("\nFEIL BRUK - USAGE:\n./oblig2 FIL.dat\n\n");
        return 0;
    }

    /*
    Vi aapner foerst en fil fp, og tar inn argumentet (filnavnet)
    fra kommandolinjen. Routernum initialiseres, dette er antall
    routere. Vi leser inn dette fra filen (4 bytes). Deretter maa
    vi bruke fseek for aa navigere oss forbi neste linjeskift.
    */
    FILE *fp = fopen(argv[1], "r");
    int routerNum = 0;
    fread(&routerNum, 4, 1, fp);
    fseek(fp, 5, SEEK_SET);

    /*
    For alle routerne i filen, opretter vi en router-peker
    og allokerer minnet til denne. Vi leser deretter inn innholdet
    fra filstroemmen med fread og setter dette som routers medlemmer.
    Til slutt settes routeren inn i mappet.
    */
    int i;
    for(i = 0; i < routerNum; i++) {

        struct router *rt;
        rt = malloc(256);

        fread(&rt->rID, 1, 1, fp);
        fread(&rt->flag, 1, 1, fp);
        fread(&rt->len, 1, 1, fp);
        fread(rt->output, rt->len, 1, fp);
        rt->output[rt->len] = '\0';

        map[rt->rID] = rt;

    }
    fclose(fp);

    /*
    Litt semi-hacky implementasjon, men vi bruker int comp som er
    inputet fra bruker til aa sjekke hva bruker velger. comp2 brukes
    inne i loekken. tempCh brukes som streng. Charcomp er flag.
    */
    int comp, comp2, comp3;;
    char *tempCh = malloc(253);
    unsigned char charComp, part, low;
    print_meny();
    scanf("%d", &comp);

    while(1) {
        //Avslutter loekken
        if(comp == 6) {
            printf("\n");
            break;

        //Denne delen printer ut en router gitt en ID
        } else if(comp == 1) {
            printf("\nHvilken ID oensker du aa printe?\n");

            scanf("%d", &comp2);
            print_router((map[comp2]));

        //Denne delen sletter en router gitt en ID
        } else if(comp == 2) {
            printf("\nHva er ID'en til routeren di vil slette?\n");

            scanf("%d", &comp2);
            delete_router(comp2);

        //Denne delen endrer flag
        } else if(comp == 3) {
            printf("\nHva er IDen til routeren du vil endre flag paa?\n");

            scanf("%d", &comp2);

            //Part er hele flag-charen, low er de siste fire bitsene
            part = map[comp2]->flag;
            low = part & 0xF;

            if(low >= 15) {
                printf("\nFEIL! ENDRINGSNUMMER FOR HOYT!\n");
            } else {

                printf("\nEr routeren aktiv? (0 = NEI 1 = JA)\n");
                scanf("%d", &comp3);
                router_set_bit(map[comp2], 7, comp3);

                printf("\nEr routeren traadloos? (0 = NEI 1 = JA)\n");
                scanf("%d", &comp3);
                router_set_bit(map[comp2], 6, comp3);

                printf("\nStotter den 5ghz? (0 = NEI 1 = JA)\n");
                scanf("%d", &comp3);
                router_set_bit(map[comp2], 5, comp3);

                /*
                TODO: Ble ikke ferdig helt her, inkrementering for laveste
                4 bits i flag... Trenger egentlig hjelp her. Dette er det
                eneste jeg ikke fikk til av kravspec. Meningen her er aa
                inkrementere de siste fire bitene av flag-charen med 1 slik
                at endringsnummeret gaar mot 15, hvor det da nektes bruker
                aa gi input, som vist over.

                low++;
                map[comp2]->flag & 0xF = low & 0xF;

                */


            }

        //Denne delen endrer navn paa en router, trigger IKKE flag-inkrement
        } else if(comp == 4) {
            printf("\nHva er IDen til routeren du vil endre navn paa?\n");

            scanf("%d", &comp2);

            printf("\nVennligst skriv navn\n");

            /*
            Kode fra instruktoer paa piazza for aa nulle ut stdin
            slik at vi ikke faar med linjeskift som terminerer
            fgets. Deretter kaller vi fgets og setter routers output
            */
            int c;
            while ( (c = getchar()) != '\n' && c != EOF ) { }
            fgets(tempCh, 253, stdin);
            router_set_output(map[comp2], tempCh);

        //Her kan vi legge inn en ny router
        } else if(comp == 5) {
            printf("\nHva er IDen til din nye router?\n");

            scanf("%d", &comp2);

            //Dersom map[comp2] er true, finnes her en router allerede
            if(map[comp2]) {
                printf("FEIL! ROUTER FINNES ALLEREDE HER!");
                comp = 9;
                continue;

            } else {

                /*
                Dersom her er ingen routere med samme ID, kan vi sette inn
                en router her. Foerst oprettes en router-peker som det
                allokeres minne til, deretter nuller vi ut stdin for aa
                fjaerne linjeskift slik at fgets kalles korrekt. Saa kan vi
                ta inn flagget. Til slutt initialiserer vi dataen til rt
                of setter den inn i mappet. Deretter kommer logikk for setting
                av flag, som setter de individuelle bittene som representere
                de ulike egenskapene.
                */
                struct router *rt;
                rt = malloc(256);

                printf("\nVennligst skriv navn\n");

                int c;
                while ( (c = getchar()) != '\n' && c != EOF ) { }
                fgets(tempCh, 253, stdin);

                charComp = 0;

                rt->rID = comp2;
                rt->flag = charComp;
                rt->len = strlen(tempCh);
                strcpy(rt->output, tempCh);

                map[rt->rID] = rt;

                printf("\nEr routeren aktiv? (0 = NEI 1 = JA)\n");
                scanf("%d", &comp3);
                router_set_bit(map[comp2], 7, comp3);

                printf("\nEr routeren traadloos? (0 = NEI 1 = JA)\n");
                scanf("%d", &comp3);
                router_set_bit(map[comp2], 6, comp3);

                printf("\nStotter den 5ghz? (0 = NEI 1 = JA)\n");
                scanf("%d", &comp3);
                router_set_bit(map[comp2], 5, comp3);

            }
        }

        //Sluttvis settes comp slik at det ikke slaar inn og meny printes
        comp = 9;
        print_meny();
        scanf("%d", &comp);
    }
    //Ved terminering av loekken maa vi frigjoere allokert minne for tempCH
    free(tempCh);

    //Denne loekken gaar gjennom map og finner antall routere (tempSum)
    int tempSum = 0;
    for(i = 0; i < 255; i++) {
        if(map[i]) {
            tempSum++;
        }
    }

    /*
    Foerst opretter vi en filskriver som skriver ut til testout.dat,
    saa maa vi skrive inn tempSum som er antall routere, og et linjeskift.
    Deretter kan vi gaa gjennom mappet og for hver gang vi treffer paa
    en router skrives in alle dataene for denne til fil. Deretter lukkes
    filskriveren
    */
    FILE *fpOut = fopen("testout.dat", "w");
    fwrite(&tempSum, 4, 1, fpOut);

    char tempLine = '\n';
    fwrite(&tempLine, 1, 1, fpOut);

    for(i = 0; i < 255; i++) {
        if(map[i]) {
            fwrite(&map[i]->rID, 1, 1, fpOut);
            fwrite(&map[i]->flag, 1, 1, fpOut);
            fwrite(&map[i]->len, 1, 1, fpOut);
            fwrite(map[i]->output, map[i]->len, 1, fpOut);
        }
    }
    fclose(fpOut);

    //Debug-loekke som printer routere
    for(i = 0; i < 255; i++) {
        if(map[i]) {
            print_router((map[i]));
        }
    }

    //Denne koden frigjoer minnet vi har allokert for routerene
    for(int i = 0; i < 255; i++) {
        if(map[i]) {
            free(map[i]);
        }
    }

}
