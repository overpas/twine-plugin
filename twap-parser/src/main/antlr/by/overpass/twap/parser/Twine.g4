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
    '[[' identifier ']]'
    ;

label
    :
    label_title translation+ comment?
    ;

label_title
    :
    '[' identifier ']'
    ;

identifier
    :
    ID
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
LOCALE : [a-zA-Z][a-zA-Z] ;
ID : [a-zA-Z0-9_]+('.'[a-zA-Z0-9_]+)* ;
WS : [ \r\t\n]+ -> channel(HIDDEN) ;
TEXT: '='' '*.*?('\n'|EOF) ;