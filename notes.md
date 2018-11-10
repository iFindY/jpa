   # JPA notes 
   
   ### Fetching 
   
   Cardinality | Fetch Mode
   ------------|-----------
   @OneToOne   | EAGER
   @ManyToOne  | EAGER
   @OneToMany  | LAZY
   @ManyToMany | LAZY
   
   - EAGER  will pull all relationship immediately
   - LAZY fetch mode will  only load the root element, 
   only after a get method on object JPA will fetch this objects into memory.
   ### Cascading Events 
   
    * PERSIST
    * REMOVE
    * MERGE
    * ALL
       
   ### Inheritance strategy 
    * SINGLE_TABLE
    * JOINED 
    * TABLE_PER_CONCRET_CLASS
    
   ### Examples 
   
    @OneToMany(fetch = EAGER)
   -----------------------
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musician_fk")
   -----------------------
    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable
   -----------------------
    @OneToOne(fetch = FetchType.LAZY) 
    @JoinTable(name = "cd_musician",
        joinColumns = @JoinColumn(name = "cd_fk"),
        inverseJoinColumns = @JoinColumn(name = "musician_fk") )
   -----------------------
    @ManyToMany
       @JoinTable(name = "cd_musician",
               joinColumns = @JoinColumn(name = "cd_fk"),
               inverseJoinColumns = @JoinColumn(name = "musician_fk"))
       private Set<Musician> musicians = new HashSet<>();
   -----------------------
    @OneToMany(cascade={PERSIST,REMOVE,MERGE})
   ---------------------
    @OneToMany(cascade=CascadeType.PERSIST)
   --------------
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
   --------------
   ### Notes 
   1. There are two ways off referencing relationships.
   You can reference a *foreign key* of the relationship in the table or create a join table of  two table keys
   referencing to each other.
   There can be uni or bi-directional relationships as well.
   *OneToMany/ManyToMany* are joined on extra table. *OneToOne/ManyToOne* are joined on column
   
   2. one-to-one relation ship default.
   when a 'cd' is inside musician and a *musician* object is inside cd.
   with `@oneToOne` annotation we can manipulate the default mapping.
   
   3. Default it will do join column on musician_id.
   Can be manipulated with `@JoinColumns("musician_fk")`
   
   4. `@OneToOne(fetch=fetchType.EAGER)` is default and fetch all data at query.
   fetchType.LAZY will load data only if asked for the data
   
   5. If you want to join in extra table you can specify this table with `@JoinTable (name="cd_musician")`
   You can also name the join column names of the entities [input is always the entity key]
   
   6. If there is a collection you can use @OneToMany [in 'cd' there is a 'musician' collection]
   and @ManyToOne [in *musician* class is a *cd* field]
   
   7. If there is a `@ManyToMany` relationship. In *cd* class is a *musician* collection
   and in *musician* class is a *cd* collection.
   JPA will see this relationship and do default configuration mapping.
   
   8. You can overwrite defaults of `@OneToMany` [in *cd* many *musicians*]
   and do join on column strategy with the `@JoinOnColumn` annotation.
   This will create a foreign key in every 'musician' to the *cd*.
   With te extra `@JoinOnColumn(name="cd_fk")` you can specify key name in *musician*.
   
   9. We can cascade all events from root entity to children by `@OneToMany(cascade={PERSIST,REMOVE,MERGE})`
   By default events are not propagated. 
   
   10. If we have inheritance in our class hierarchy, JPA will assume single table per class strategy.
   It means all attributes will end up in root entity table. Same as `@Inheritance(strategy = InheritanceType.SINGLE_TABLE)`.
   Inheritance annotation are on the root entity's.

   