grammar Twine;

@header{
    package by.overpass.twap.parser;
}

twine
    :
    section+
    ;

section
    :
    section_title label+
    ;

section_title
    :
    '[[' ID ']]'
    ;

label
    :
    label_title translation+ comment?
    ;

label_title
    :
    '[' ID ']'
    ;

translation
    :
    LOCALE TEXT
    ;

comment
    :
    COMMENT TEXT
    ;

COMMENT : 'comment' ;
LOCALE : 'en' | 'ru' | 'fi' ;
ID : [a-zA-Z0-9_]+('.'[a-zA-Z0-9_]+)* ;
WS : [ \r\t\n]+ -> channel(HIDDEN) ;
TEXT: ('='' '*.+?('\n'|EOF)) ;