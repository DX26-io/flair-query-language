parser grammar SearchQLParser;

options { tokenVocab=SearchQLLexer; }


parse:
    ( statement | error) * EOF;

error
 : UNEXPECTED_CHAR
   {
     throw new RuntimeException("UNEXPECTED_CHAR=" + $UNEXPECTED_CHAR.text);
   }
 ;

statement
 : aggregation_statements by_statement? where_statement? orderby_statement?
 ;

aggregation_statements
 : aggregation_statement (COMMA aggregation_statement?)*
 ;

aggregation_statement
 : aggregation_function OPEN_PAR? feature? CLOSE_PAR?
 ;

where_statement
 : K_FILTER K_BY (conditions)?
 ;

by_statement
 : K_BY features?
 ;

orderby_statement
 : K_ORDER_BY feature? order_direction?
 ;

order_direction
 : K_ASC | K_DESC
 ;

comparison
 : LT | LT_EQ | GT | GT_EQ | EQ | NOT_EQ1 | NOT_EQ2
 ;

condition_in
 : feature OPEN_PAR? any_name? (COMMA any_name)* CLOSE_PAR?
 ;

condition_compare
 : feature comparison? any_name?
 ;

condition
 : condition_in
 | condition_compare
 ;

conditions
 : condition ( COMMA condition? )*
 ;

features
 : feature ( COMMA feature? )*
 ;

any_name:
  IDENTIFIER
 | STRING_LITERAL
 | NUMERIC_LITERAL
 | OPEN_PAR any_name CLOSE_PAR
 ;

date_range
 : any_name
 ;

aggregation_function
 : any_name
 ;

feature
 : any_name
 ;