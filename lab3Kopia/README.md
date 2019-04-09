# EDAF75, project report

This is the report for

 + Andreas Erlandsson, `stil-id`
 + Albin Olsson, `al1100ol-s`
 + Martin Fredlund, `ma1233fr-s`

We solved this project on our own, except for:

 + The Peer-review meeting


## ER-design

The model is in the file [`er-model.png`](er-model.png):

<center>
    <img src="er-model.png" width="100%">
</center>

(The image above describes the model from lecture 4, you
must replace the file '`er-model.png`' with an image of your
own ER-model).


## Relations

The ER-model above gives the following relations (neither
[Markdown](https://docs.gitlab.com/ee/user/markdown.html)
nor [HTML5](https://en.wikipedia.org/wiki/HTML5) handles
underlining withtout resorting to
[CSS](https://en.wikipedia.org/wiki/Cascading_Style_Sheets),
so we use bold face for primary keys, italicized face for
foreign keys, and bold italicized face for attributes which
are both primary keys and foreign keys):

+ products(**product_name**)
+ materials(**ingredient**, amount, unit)
+ used_materials(**used_amount**, _unit_, **_ingredient_**, **product_name**)
+ restocks(**buy_amount**, **buy_date**, **buy_time**, **_ingredient_**)
+ pallets(**pallet_id**, production_date, production_time, blocked, _product_name_, _customer_name_, _delivery_date_, _delivery_time_)
+ deliveries(**delivery_date**, **delivery_time**, **_customer_name_**, **_pallet_id_**)
+ customers(**customer_name**, address)
+ orders(**due_date**, **due_time**, **_product_name_**, **_customer_name_**)



## Scripts to set up database

The scripts used to set up and populate the database are in:

 + [`create-schema.sql`](create-schema.sql) (defines the tables), and
 + [`initial-data.sql`](initial-data.sql) (inserts data).

So, to create and initialize the database, we run:

```shell
sqlite3 krusty-db.sqlite < create-schema.sql
sqlite3 krusty-db.sqlite < initial-data.sql
```

(or whatever you call your database file).

## How to compile and run the program

This section should give a few simple commands to type to
compile and run the program from the command line, such as:

```shell
./gradlew run
```

or

```shell
javac -d bin/ -cp src src/krusty/Main.java
java -cp bin:sqlite-jdbc.jar krusty.Main
```

or, if you put your commands into a `Makefile`:

```shell
make compile
make run
```
