create table Users (
id integer NOT NULL,
username varchar(256) NOT NULL,
password varchar(256) NOT NULL,
PRIMARY KEY (id)
);

create table Laskuttaja (
yrityksenNimi varchar(256) NOT NULL,
versio integer NOT NULL,
alvTunniste varchar(16) NOT NULL,
nimi varchar(256) NOT NULL,
katuosoite varchar(256) NOT NULL,
postinumero varchar(256) NOT NULL,
kaupunki varchar(256) NOT NULL,
puhelinnumero varchar(256) NOT NULL,
sahkopostiOsoite varchar(256) NOT NULL,
/*Kokonaisluku, >= 0.*/
laskujaLahetetty integer NOT NULL,
tilinumero varchar(256) NOT NULL,
tilinumeronPankki varchar(256) NOT NULL,
tilinumeronSwiftBic varchar(256) NOT NULL,
PRIMARY KEY (yrityksenNimi, versio)
);

create table Pankkiviivakoodi (
/*Merkkijono (koostuu numeroista), jonka avulla asiakas voi maksaa laskun helposti. Lisätietoja http://www.fkl.fi/teemasivut/sepa/tekninen_dokumentaatio/Dokumentit/Pankkiviivakoodi-opas.pdf*/
pankkiviivakoodi varchar(62) NOT NULL,
/*Laskun viitenumero merkkijonoksi muutettuna. Vaatimukset viitteelle mm. http://www.fkl.fi/teemasivut/sepa/tekninen_dokumentaatio/Dokumentit/kotimaisen_viitteen_rakenneohje.pdf*/
viiteTarkisteella varchar(256) NOT NULL,
/*Positiivinen 18 numeron tarkkuudella oleva desimaaliluku. Kun oletetaan, että laskutettavat summat ovat suuruusluokaltaan alle 10^10, niin summat ovat talletettuina kahdeksan desimaalin tarkkuudella. Käytettävä tietotyyppi parametreineen mahdollistaa tarpeeksi tarkat desimaaliluvut erilaisia laskutoimituksia varten.*/
laskunSumma decimal(13, 4) NOT NULL,
erapaiva date NOT NULL,
PRIMARY KEY (pankkiviivakoodi)
);

create table Lasku (
/*Kokonaisluku, > 0.*/
laskunNumero integer NOT NULL,
/*Kokonaisluku, > 0. Voi olla null, koska yleensä ei ole tarvetta viitata aiemmin muodostettuun laskuun.*/
viittausAiempaanLaskuun  integer,
laskuttaja varchar(256) NOT NULL,
laskuttajanVersio integer NOT NULL,
/*Kokonaisluku, >= 0.*/
maksuaika integer NOT NULL,
paivays date NOT NULL,
/*Kokonaisluku, 0 <= viivastyskorko <= 100.*/
viivastyskorko integer NOT NULL,
maksuehto varchar(256) NOT NULL,
lisatiedot varchar(256),
onkoMaksettu boolean NOT NULL,
/*Merkkijono (koostuu numeroista), jonka avulla asiakas voi maksaa laskun helposti. Lisätietoja http://www.fkl.fi/teemasivut/sepa/tekninen_dokumentaatio/Dokumentit/Pankkiviivakoodi-opas.pdf*/
pankkiviivakoodi varchar(62) NOT NULL,
PRIMARY KEY (laskunNumero),
FOREIGN KEY (laskuttaja, laskuttajanVersio) REFERENCES Laskuttaja(yrityksenNimi, versio),
FOREIGN KEY (pankkiviivakoodi) REFERENCES Pankkiviivakoodi(pankkiviivakoodi)
);

create table Asiakas (
/*Kokonaisluku, > 0.*/
asiakasnumero integer NOT NULL,
versio integer NOT NULL,
nimi varchar(256) NOT NULL,
katuosoite varchar(256) NOT NULL,
postinumero varchar(256) NOT NULL,
kaupunki varchar(256) NOT NULL,
/*Kokonaisluku, >= 0.*/
laskujaLahetetty integer NOT NULL,
email varchar(256) NOT NULL,
PRIMARY KEY (asiakasnumero, versio)
);

create table Suorite (
/*Kokonaisluku, > 0.*/
suoritteenNumero serial NOT NULL,
/*Kokonaisluku, > 0.*/
lasku integer,
kuvaus varchar(256) NOT NULL,
/*Kokonaisluku, > 0.*/
tilaaja integer NOT NULL,
tilaajanVersio integer NOT NULL,
/*Kokonaisluku, > 0.*/
vastaanottaja integer NOT NULL,
vastaanottajanVersio integer NOT NULL,
/*Positiivinen 18 numeron tarkkuudella oleva desimaaliluku. Kun oletetaan, että sarakkeeseen talletettavat luvut ovat alle 10^10, niin luvut ovat talletettuina kahdeksan desimaalin tarkkuudella. Käytettävä tietotyyppi parametreineen mahdollistaa tarpeeksi tarkat desimaaliluvut erilaisia laskutoimituksia varten.*/
maara decimal(13, 4) NOT NULL,
/*Positiivinen 18 numeron tarkkuudella oleva desimaaliluku. Kun oletetaan, että sarakkeeseen talletettavat luvut ovat alle 10^10, niin luvut ovat talletettuina kahdeksan desimaalin tarkkuudella. Käytettävä tietotyyppi parametreineen mahdollistaa tarpeeksi tarkat desimaaliluvut erilaisia laskutoimituksia varten.*/
maaranYksikot varchar(8) NOT NULL,
/*Positiivinen 18 numeron tarkkuudella oleva desimaaliluku. Kun oletetaan, että sarakkeeseen talletettavat luvut ovat alle 10^10, niin luvut ovat talletettuina kahdeksan desimaalin tarkkuudella. Käytettävä tietotyyppi parametreineen mahdollistaa tarpeeksi tarkat desimaaliluvut erilaisia laskutoimituksia varten.*/
aHintaVeroton decimal(13, 4) NOT NULL,
/*Kokonaisluku, 0 <= alvProsentti<= 100.*/
alvProsentti integer NOT NULL,
/*Tähän sarakkeeseen talletaan mahdollisia suoritteiden aloitusaikoja tai pelkkä päiväys.*/
alkuaika timestamp NOT NULL,
/*Tähän sarakkeeseen talletaan mahdollisia suoritteiden lopetusaikoja.*/
loppuaika timestamp,
PRIMARY KEY (suoritteenNumero),
FOREIGN KEY (lasku) REFERENCES Lasku(laskunNumero),
FOREIGN KEY (tilaaja, tilaajanVersio) REFERENCES Asiakas(asiakasnumero, versio),
FOREIGN KEY (vastaanottaja, vastaanottajanVersio) REFERENCES Asiakas(asiakasnumero, versio)
);

create table Asetukset (
asetuksetID integer NOT NULL,
pdfOletusvientipolku varchar(256),
PRIMARY KEY (asetuksetID)
);