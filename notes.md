   # JPA notes 
   
   ### Fetching 
   
   Cardinality | Fetch Mode
   ------------|-----------
   @OneToOne   | EAGER
   @ManyToOne  | EAGER
   @OneToMany  | LAZY
   @ManyToMany | LAZY
   
   - **EAGER**  will pull all relationship immediately
   - **LAZY** fetch mode will  only load the root element, 
   only after a get method on object JPA will fetch this objects into memory.
   
   ### Cascading Events  
  - **PERSIST**
  - **REMOVE**
  - **MERGE**
  - **ALL**
       
   ### Inheritance strategy 
  - **SINGLE_TABLE**
  - **JOINED**
  - **TABLE_PER_CLASS**
  
  ### Queries 
  
  - **Dynamic** : dynamically specified at runtime 
  - **Named**   : static an unchangeable
  - **Query**   : return objects which has to be casted 
  - **TypedQuery** : have a return Type and a search httpType 
  
  1. EntityManager.createQuery(JPQL)
  2. EntityManager.createNamedQuery(nameOfQuery) *inherent from Query and can be used most of the time* 
  3. Pagination: fix amount of returned objects 
  4. TypedQuery<myClass> have a return Type  and the httpType to search for in the query setup em.createQuery(jpql,myClass.class).
  
  ### EJB
  Stateful | Starless
  ---------|------------------
  application scoped    | request scoped 
  session scoped        |
  conversation scoped   |
  
   
  ### JPQL Syntax
      
      SELECT    <select clause>
      FROM      <from clause>
      [WHERE    <where clause>]
      [ORDER BY <order by clause>] 
      [GROUP BY <group by clause>] 
      [HAVING <having clause>]
      
      <function> AVG, COUNT, MAX, MIN, SUM
      
      <operators> =, >, >=, <, <=, <>, 
                 [NOT] BETWEEN, 
                 [NOT] IN, 
                 [NOT] LIKE, 
                 IS [NOT] NULL, 
                 IS [NOT] EMPTY, 
                 [NOT] MEMBER [OF]
      
      <num exp.> ABS, SQRT, MOD, SIZE, INDEX
      
      <string exp.>CONCAT, SUBSTRING, TRIM, LOWER, UPPER, LENGTH, LOCATE
      
      <date exp.> CURRENT_DATE, CURRENT_TIME, CURRENT_TIMESTAMP

  ### Entity life cycle
    - @PrePersist and @PostPersist
    - @PreUpdate and @PostUpdate
    - @PreRemove and @PostRemove 
    - @PostLoad
    - Methods can not be finla or static 
    
    
  ### Transactional Policies
   Package      | Description
  --------------|------------
   REQUIRED     | Always propagates the transaction(default)
   REQUIRES_NEW | creates new transaction befog executing a method
   SUPPORTS     | inherent the client transaction context 
   MANDATORY    | require a transaction before executing a method
   NOT_SUPPORTED| can not be invoiced in transactional context 
   NEVER        | must not be invoiced from a transactional client 
     
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
   -----------------------
    @OneToMany(cascade=CascadeType.PERSIST)
   -----------------------
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
    public class Item{...}
   -----------------------
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
    @DiscriminatorColumn(name="DISC",discriminatorType=CHAR)
    public class Item{...}
   -----------------------
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
    @DiscriminatorColumn(name="DISC",discriminatorType=CHAR)
    @DiscriminatorValue("I")
    public class Item{...}
   -----------------------
    @MappedSuperClass
    public class Item{...}
   -----------------------
    TypedQuery<Book> query = em.createQuery(
          "SELECT b FROM Book b
    WHERE b.unitCost > ?1 AND b.nbOfPage < ?2", Book.class);
    
    query.setParameter(1, unitCost); 
    query.setParameter(2, nbOfPage); 
    List<Book> books = query.getResultList();
   -----------------------
    TypedQuery<Book> query = em.createQuery(
          "SELECT b FROM Book b
    WHERE b.unitCost > :cost AND b.nbOfPage < :pages", Book.class);
    
    query.setParameter("cost", unitCost); 
    query.setParameter("pages", nbOfPage); 
    List<Book> books = query.getResultList();
   -----------------------
    TypedQuery<Book> query = em.createQuery(
       "SELECT b FROM Book b
        WHERE  b.publicationDate < :pubDate", Book.class);
        
    query.setParameter("pubDate", new Date(), TIMESTAMP); 
    query.setMaxResults(10);
    List<Book> books = query.getResultList();
   -----------------------
    @Entity
    @NamedQuery(name = "ExpensiveBooks", query =
    "SELECT b FROM Book b
    WHERE b.unitCost > :cost AND b.nbOfPage < :pages")
    public class Book {
    }//
    Query query = em.createNamedQuery("ExpensiveBooks");
    List books = query.getResultList();
    
    TypedQuery<Book> query = em.createNamedQuery ( "ExpensiveBooks", Book.class);
    query.setParameter("cost", unitCost); 
    query.setParameter("pages", nbOfPage); 
    query.setMaxResults(10);
    List<Book> books = query.getResultList();   
   -----------------------
    @Entity
    @NamedQueries({
     @NamedQuery(name = "ExpensiveBooks", query =
          "SELECT b FROM Book b
    WHERE b.unitCost > 29 AND b.nbOfPage < 700"), @NamedQuery(name = "PublishedBooks", query =
          "SELECT b FROM Book b
           WHERE  b.publicationDate < :pubDate"),
    @NamedQuery(name = Book.FIND_ALL, query =
          "SELECT b FROM Book b")
    })
    public class Book {
    public static final String FIND_ALL = "Book.All";
    }
  -----------------------  
    public Book raiseUnitCost(Long id, Float raise) {
      Book book = em.find(Book.class, id);
      if (book != null) {
        tx.begin();
        book.setUnitCost(book.getUnitCost() + raise);
        tx.commit();
       }
    return book;
    }
   -----------------------
    public Book raiseUnitCost(Book book, Float raise) { 
    Book bookToBeUpdated = em.merge(book); 
    tx.begin();
    bookToBeUpdated.setUnitCost(bookToBeUpdated.getUnitCost() + raise); 
    //em.remove(bookToBeDeleted);
    tx.commit;
    return book;
    }
   -----------------------    
    @Entity
    @EntityListeners({
            AgeCalculationListener.class,
            ValidationListener.class
    })
   -----------------------     
    @AroundInvoke
    private Object intercept(InvocationContext ic) throws Exception {
        logger.entering(ic.getTarget().getClass().getName(), ic.getMethod().getName());
        try {
            return ic.proceed();
        } finally {
            logger.exiting(ic.getTarget().getClass().getName(), ic.getMethod().getName());
        }
    }
 -----------------------    
    @Entity
    public class Book {
        @Id
        private Long id;
        private String title; private String description;
        
        @PrePersist
        @PreUpdate
        private void validate() {
            if (title == null || "".equals(title))
            throw new IllegalArgumentException("Invalid title");
            if (description == null || "".equals(description))
            throw new IllegalArgumentException("Invalid description");
         } 
    }
 

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
   The `SINGLE_TABLE` strategy will create an extra column `DTYPE` which will describe entity types merged in this table.
   You can also change the `DTYPE` column name by `@Discriminatorolumn(...)` and set the input for each entity 
   by annotating the classes with `@DiscriminatorValue`.
   The `TABLE_PER_CLASS` would create for every entity a Table consisting of own columns plus superclass columns.
    
   11. With ` @MappedSuperClass` we can inherent fields from *supper class*,which later will be persistent 
   as columns by the leaf entities. 
   
   12. If you use `Named`  but not `Typed`  query, the result will return a list of untyped Objects  
   
   13. Entity listeners can externalise some pre-post persist behavior by defining some methods with @Pre and @Post annotations,
    which will be executed on life cycle calls. Wth the XML configuration [myMappingFile.xml] you can define global entity listeners.
    You can exclude default listeners with `@ExcludeDefaultListiners`.
    The mapping file should be register in the persistence.xml under <mapping-file> tag [META-INF/myMappingFile.xml].
    
   14. If there is a `@Transactionl` annotation you can skipp tx.begin() and tx.commit(). 
   The `@Transactionl`  annotation intercepts every method and start and end a transaction. 
   Technically `@Transactionl` is an Interceptor. Can annotation  class or method.  
    
   15. Interceptors can handel in and outgoing workflow of a bean with the `finaly` block
    
   16. Producer creates objects and disposers destroys objects. The Dispose Method has parameter sam as Produces return httpType
   and the annotation is inside the parameter block and not above method. If there are qualifiers specialise this object you
   have to append them to the dispose annotation too. the Disposer Method must be declare in the same class as the producer.
   The Dispose method is called automatic after the client context ends.
    
   17. the decorators wrap around class, add additional logic to a class named delegate.
   The decorator implements same interface as the delegate object with needed methods. the delegate object is injected inside the decoratr
   with @Inject @Delegate and perform some logic inside the decorators implemented interface methods.  
   The decorators has to be enabled in the beans.xml .
   Abstract classes implementing an interface do not have to implement all methods.
   Interceptor = Technical 
   Decorator = Business 
   
   18. Events is a observer/observable pattern 
   
   19. CDI is a sub container of java EE
   
   20. you can not @Inject Entities, tats why you have to insentient it. Entity is not a CDI bean and into it can not be injected.
   But you have Entity Listeners 
   
    