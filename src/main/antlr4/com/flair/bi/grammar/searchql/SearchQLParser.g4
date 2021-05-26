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
 : aggregation_statements SPACES? by_statement? SPACES? where_statement? SPACES? orderby_statement?
 ;

aggregation_statements
 : aggregation_statement SPACES? (COMMA SPACES? aggregation_statement?)*
 ;

aggregation_statement
 : aggregation_function SPACES? OPEN_PAR? SPACES? feature? SPACES? CLOSE_PAR?
 ;

where_statement
 : FILTER_BY SPACES? conditions?
 ;

by_statement
 : K_BY SPACES? features?
 ;

orderby_statement
 : K_ORDER_BY SPACES? feature? SPACES? order_direction?
 ;

order_direction
 : K_ASC | K_DESC
 ;

comparison
 : LT | LT_EQ | GT | GT_EQ | EQ | NOT_EQ1 | NOT_EQ2
 ;

condition_in
 : feature SPACES? OPEN_PAR? SPACES? any_name? SPACES? (COMMA SPACES? any_name?)* CLOSE_PAR?
 ;

condition_compare
 : feature SPACES? comparison? SPACES? any_name?
 ;

condition
 : condition_in
 | condition_compare
 ;

conditions
 : condition SPACES? ( COMMA SPACES? condition? )*
 ;

features
 : feature SPACES? ( COMMA SPACES? feature? )*
 ;

any_name:
  IDENTIFIER
 | STRING_LITERAL
 | NUMERIC_LITERAL
 | OPEN_PAR SPACES? any_name SPACES? CLOSE_PAR
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