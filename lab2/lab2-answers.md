4:
==

## a) Which relations have natural keys?

Vi har natural keys i customer "user_name", i theaters "theater_name", i movies "movie_name, production_year", i screenings "screening_time, screening_date, theater_name"


## b) Is there a risk that any of the natural keys will ever change?

Ja, det är ofta problemet med natural keys och därför vissa förespråkar att använda invented keys istället.

## c) Are there any weak entity sets?

Weak entity set är när entityn inte kan skapa en unik nyckel med sina egna attribut. Ett exemple på en weak entity.

## d) In which relations do you want to use an invented key. Why?

Vi andvänder invented key i tickets, efter som det inte finns några andra möjligheter att skapa en unik nyckel. Då en användare kan ha mer än en biljett till en film.

6:
==

customers(_user_name_, full_name, pass_word)		
tickets(_ticket_id_, /user_name/, /screening_time/, /screening_date/, /theater_name/)		
movies(_movie_name_, _production_year_, playtime, imdb_key)		
screenings(_screening_date_, _screening_time_, _theater_name_, /production_year/, /movie_name/)
theaters(_theater_name_, capacity)		

7:
==
Ett alterntiv är att räkna antalet tickets för en viss screening och sedan jämföra med antalet platser på bion. Då får vi ett svar genom att använda 3 olika typer av tabeller. Ett andra sätt är att skapa en ny tabell som håller koll på antalet tickets till varje screening samt capaciteten på varje bio.


