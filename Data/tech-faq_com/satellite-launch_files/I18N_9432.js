/* This source code is Copyright (c) Vibrant Media 2001-2008 and forms part of the patented Vibrant Media product "IntelliTXT" (sm). */
if('undefined'==typeof $iTXT) {var $iTXT={};} if('undefined'==typeof $iTXT.I18N) {$iTXT.I18N=function(c,l) {var langs={de:"de",at:"de",ch:"de",fr:"fr",gb:"en-gb",uk:"en-gb",it:"it",nl:"nl",se:"sv",no:"no",dk:"da",jp:"ja",cn:"zh",es:"es",fi:"fi",pl:"pl",ru:"ru"};var trans={en:{sCC:"$",sspl:"Advertisement",swti:"What's this?",slm:"LEARN MORE",ssch:"Searching...",sbn:"Buy now",scls:"Close",sEet:"End time",sEcb:"Place bid",sEsn:"Seller",iert:"Articles related to <b>_KEYWORD_</b>",ierao:"Related Articles on ",iera:"Related Articles",wcfa:"Featured article",iist:"Shop for items related to ",iirt:"Search the web for info related to ",iimsnt:"Live Search for ",iivt:"Show video related to ",iiat:"Information related to ",gRCSrcTSt:"Search this site:",gRCSrc:"Search",gRCMor:"MORE"},"en-gb":{sCC:"&pound;"},fr:{sCC:"&euro;",sspl:"Publicit&#233;",swti:"Qu'est-ce que c'est?",sbn:"Achat maintenant",scls:"Fermer",sEet:"Temps restant",sEcb:"Ench&#233;rir",sEsn:"Vendeur",iert:"Liens similaires",ierao:"Articles connexes sur ",iera:"Articles connexes",wcfa:"Article phare",iist:"Boutique pour les produits apparent&#233;s &#224; ",iirt:"Rechercher sur le web une info apparent&#233;e &#224; ",iivt:"Visualiser les vid&#233;os apparent&#233;es &#224; ",iiat:"Information apparent&#233;e &#224; ",gRCSrcTSt:"Rechercher sur le site:",gRCSrc:"Rechercher",gRCMor:"PLUS"},de:{sCC:"&euro;",sspl:"Werbung",swti:"was ist das?",sbn:"Jetzt kaufen",scls:"Schlie&#223;en",sEet:"Angebotsende",sEcb:"Bieten",sEsn:"Verk&#228;ufer",iert:"Weitere Links",ierao:"Themenverwandte Artikel ",iera:"Themenverwandte Artikel",wcfa:"Artikelhighlight",iist:"Finden Sie weitere Produkte zum Thema ",iirt:"Finden Sie weitere Informationen zum Thema ",iivt:"Videos zum Thema ",iiat:"Weitere Informationen zum Thema ",gRCSrcTSt:"Suche auf:",gRCSrc:"Suchen",gRCMor:"MEHR INFO",mlm:true},es:{sCC:"&euro;",sspl:"Publicidad",swti:"&#191; Qu&#233; es esto?",scls:"cierre",iert:"Art&#237;culos Relacionados",ierao:"Otros art&#237;culos sobre ",iera:"Otros art&#237;culos",wcfa:"Art&#237;culo principal",iist:"Compra art&#237;culos relacionados con ",iirt:"B&#250;squeda para informaci&#243;n relacionado con ",iivt:"Mostrar video relacionado con ",iiat:"Informaci&#243;n relacionado con ",gRCSrcTSt:"B&#250;squeda del sitio:",gRCSrc:"Buscar",gRCMor:"M&#193;S"},it:{sCC:"&euro;",sspl:"Pubblicit&#224;",swti:"Che cos'&#232;?",sbn:"Compra ora",scls:"Chiudi",sEet:"Ora di scadenza",sEcb:"Fai un'offerta",sEsn:"Venditore",iert:"Link similari",ierao:"Altri articoli su ",iera:"Altri articoli",wcfa:"In primo piano",iist:"Acquista prodotti in relazione a ",iirt:"Cerca nel web informazioni su ",iivt:"Mostra video su ",iiat:"Informazioni su ",gRCSrcTSt:"Cerca nel sito:",gRCSrc:"Cerca",gRCMor:"VAI"},nl:{sCC:"&euro;",sspl:"Advertentie",swti:"Wat is dit?",scls:"Sluiten",sEet:"End time",iert:"Informatie in verband met <b>_KEYWORD_</b>",iist:"Shop voor een item in verband met ",iirt:"Zoek op het net voor informatie in verband met ",iivt:"Laat een video zien in verband met ",iiat:"Informatie in verband met ",gRCSrcTSt:"Zoeken op deze site:",gRCSrc:"Zoeken",gRCMor:"MEER"},sv:{sCC:"kr ",sspl:"Annons",swti:"Vad &#228;r detta?",sbn:"K&#246;pa nu",scls:"St&#228;nga",sEet:"Sluttid",sEcb:"L&#228;gg bud",sEsn:"s&#228;ljare",iert:"Relaterte l&#228;nkar",iist:"Handla varor relaterade till ",iirt:"S&#246;k p&#229; webben f&#246;r info relaterad till ",iivt:"Spela upp video relaterad till ",iiat:"Information relaterad till ",gRCSrcTSt:"S&#246;k p&#229; sidan:",gRCSrc:"S&#246;k",gRCMor:"MER"},no:{sCC:"kr ",sspl:"Annonse",swti:"Hva er dette?",sbn:"Kj\u00D8p n\u00E5",scls:"Lukk vindu",sEet:"Sluttdato",sEcb:"Legg inn bud",sEsn:"Selger",iert:"Relaterte lenker",iist:"Shop etter relaterte produkter ",iirt:"S&#248;k p&#229; nettet for mer informasjon av ",iivt:"Se video p&#229; ",iiat:"Informasjon relatert til ",gRCSrcTSt:"S&#248;k p&#229; siden:",gRCSrc:"S&#248;ke",gRCMor:"MER"},da:{sCC:"kr ",sspl:"Annonce",swti:"Hvad er dette?",sbn:"K\u00D8b nu",scls:"Luk vindue",sEet:"Slut",sEcb:"Byd",sEsn:"S\u00E6lger",iert:"Lignende links",iist:"Shop for ting relateret til ",iirt:"S&#248;g p&#229; nettet for ting relateret til ",iivt:"Vis video relateret til ",iiat:"Information relateret til ",gRCSrcTSt:"S&#248;g p&#229; siden:",gRCSrc:"S&#248;gning",gRCMor:"MERE"},fi:{sCC:"&euro;",sspl:"Mainos",swti:"Mik&#228; t&#228;m&#228; on?",ssch:"Etsim&#228;ss&#228;...",scls:"Sulje"},ru:{sCC:"\u0440\u0443\u0431",sspl:"\u0420\u0435\u043A\u043B\u0430\u043C\u0430",swti:"\u0447\u0442\u043E \u044D\u0442\u043E?",slm:"\u0423\u0437\u043D\u0430\u0442\u044C \u043F\u043E\u0434\u0440\u043E\u0431\u043D\u0435\u0435",ssch:"\u041F\u043E\u0438\u0441\u043A...",sbn:"\u041A\u0443\u043F\u0438\u0442\u044C \u0421\u0435\u0439\u0447\u0430\u0441",scls:"\u0437\u0430\u043A\u0440\u044B\u0442\u044C (\u043E\u043A\u043D)",sEet:"\u0414\u043E \u043E\u043A\u043E\u043D\u0447\u0430\u043D\u0438\u044F",sEcb:"\u0421\u0434\u0435\u043B\u0430\u0442\u044C \u0441\u0442\u0430\u0432\u043A\u0443",sEsn:"\u041F\u0440\u043E\u0434\u0430\u0432\u0435\u0446",iert:"\u041F\u043E\u0445\u043E\u0436\u0438\u0435 \u0441\u0442\u0430\u0442\u044C\u0438",ierao:"\u041F\u043E\u0445\u043E\u0436\u0438\u0435 \u0441\u0442\u0430\u0442\u044C\u0438 ",iera:"\u041F\u043E\u0445\u043E\u0436\u0438\u0435 \u0441\u0442\u0430\u0442\u044C\u0438 ",wcfa:"\u041F\u043E\u0434\u043E\u0431\u0440\u0430\u043D\u043D\u0430\u044F \u0421\u0442\u0430\u0442\u044C\u044F",iist:"\u041A\u0443\u043F\u0438\u0442\u044C \u0442\u043E\u0432\u0430\u0440 \u0441\u0445\u043E\u0436\u0438\u0439 \u0441 ",iirt:"\u041F\u043E\u0438\u0441\u043A \u0432 \u0441\u0435\u0442\u0438 \u0438\u043D\u0444\u043E\u0440\u043C\u0430\u0446\u0438\u0438 \u043F\u043E ",iivt:"\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u044C \u0432\u0438\u0434\u0435\u043E \u0441\u0445\u043E\u0436\u0435\u0435 \u0441 ",iiat:"\u0418\u043D\u0444\u043E\u0440\u043C\u0430\u0446\u0438\u044F \u043E\u0442\u043D\u043E\u0441\u044F\u0449\u0430\u044F\u0441\u044F \u043A ",gRCSrcTSt:"\u041F\u043E\u0438\u043A \u043F\u043E \u0441\u0430\u0439\u0442\u0443:",gRCSrc:"\u041F\u043E\u0438\u0441\u043A",gRCMor:"\u0414\u0410\u041B\u0415\u0415"},pl:{sCC:"z\u0142",sspl:"Reklama",swti:"Co to jest?",slm:"DOWIEDZ SI\u0118 WI\u0118CEJ",ssch:"Trwa przeszukiwanie",sbn:"Kup teraz",scls:"Zamknij",sEet:"Koniec",sEcb:"Z\u0142\u00f3\u017c Ofert\u0119",sEsn:"Sprzedawca",iert:"Artyku\u0142y na podobny temat <b>_KEYWORD_</b>",iist:"Kupuj powi\u0105zane przedmioty ",iirt:"Szukaj w Internecie Informacji na podobny temat ",iivt:"Poka\u017c Video na podobny temat ",iiat:"Informacja na podobny temat ",gRCSrcTSt:"Szukaj na tej Stronie:",gRCSrc:"Szukaj",gRCMor:"Wi\u0119cej"},ja:{sCC:"&yen;",sspl:"\u30b9\u30dd\u30f3\u30b5\u30fc\u30ea\u30f3\u30af",swti:"\u3053\u306e\u5e83\u544a\u306b\u3064\u3044\u3066",sbn:"\u4eca\u8cb7\u7269",scls:"\u9589\u3058\u308b"},zh:{sCC:"&yen;",sspl:"\u5e7f\u544a",swti:"\u8fd9\u662f\u4ec0\u4e48\u5e7f\u544a",sbn:"\u4eca\u8cb7\u7269",scls:"\u5173\u5e7f\u544a"}};this.country=function() {return(!c)?"":c.toLowerCase();}();var co=this.country;this.language=function() {if(trans[l]) {return l;} else if(trans[langs[co]]) {return langs[co];} else {return"en";}}();this.isMLM=function() {return(null!=trans[langs[co]]&&trans[langs[co]].mlm)?true:false;}();for(var lProp in trans.en) {var lang=this.language;this[lProp]=function() {if("undefined"!=typeof trans[lang][lProp]) {return trans[lang][lProp];} else {return trans.en[lProp];}}();} this.setGlobals=function() {for(var prop in trans.en) {window[prop]=this[prop];}};};} ; 