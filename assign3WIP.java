 /*
 * Team 9 Assignment 3
 * CST338 Software Design
 * 23 March 2019
 */

   public static void main(String[] args)
   {
     char[] cardType = {'A', '2', '3', '4', '5', '6', '7', 
            '8', '9', 'T', 'J', 'Q', 'K'};
     char cardValue;
     Card legal1 = new Card();
     Card legal2 = new Card('5', Card.Suit.SPADES);
     Card illegal = new Card('Y', Card.Suit.SPADES);
     System.out.println("Just something");
     
     Hand testHand = new Hand();
     testHand.takeCard(legal1);
     testHand.takeCard(legal2);
     System.out.println(testHand.toString());
     testHand.resetHand();
     System.out.println(testHand.toString());
     
     Random rand = new Random();
     
     for(int i = 0; i < Hand.MAX_CARDS; i++) 
     {
        cardValue = cardType[rand.nextInt(13)];
        
        Card newCard = new Card(cardValue, Card.Suit.SPADES); 
        testHand.takeCard(newCard);
     }
     
     System.out.println(testHand);
     
     while (testHand.getNumCards() > 0) 
     {
        System.out.println(testHand.playCard());
     }
     
   }

/*
 * One object of class Hand represents a hand in a card game. It is compri
 */ 
class Hand
{
   public static final int MAX_CARDS = 52;
   private Card[] myCards;
   private int numCards;
  
  public Hand()
  {
    this.numCards = 0;
    this.myCards = new Card[MAX_CARDS];
  }
  
  /*
   * This method moves all the card objects from the Hand object.
   */ 
  public void resetHand()
  {
     for(int i = 0; i < myCards.length; i++)
     {
       this.myCards[i] = null;
     }
    this.numCards = 0;
  }
  
  /*
   * This method adds a Card object to the next slot in the Hand object.
   */ 
  public boolean takeCard(Card card)
  {
    if (card == null)
      return false;
    // make a copy 
    Card cardCopy = new Card(card);
    if (this.numCards < this.myCards.length)
    {
       this.myCards[this.numCards] = cardCopy;
       this.numCards++;
       return true;
    }
    else
      return false;
  }
  
  /*
   * This method returns the top card from the Hand object and removes it.
   */ 
  public Card playCard()
  {
     Card returnCard = this.myCards[this.numCards - 1];
     this.myCards[this.numCards - 1] = null;
     this.numCards--;
     return returnCard;
  }

  /*
   * This method returns the value of numCards for the Hand object.
   */
  public int getNumCards()
  {
    return this.numCards;
  }
  
  /*
   * This method creates a string representation of the Hand object.
   */ 
  public String toString()
  {
    String returnString = "(";
    for(int i = 0; i < this.numCards; i++)
    {
      if (i == this.numCards - 1)
      {
        returnString += this.myCards[i].toString();
      }
      else
         returnString += this.myCards[i].toString() + ", ";
    }
    returnString += ")";
    return returnString;
  }
  //Accessor for an individual card. Returns a card with errorFlag =
  //true if k is bad
  public Card inspectCard(int k)
  {
    //if (myCards[k])
    //  return myCards[k];
      
      
  }
}

class Card
{
   
   //Include three members char value,Suit suit,boolean errorFlag.
   private char value;
   private Suit suit;
   private boolean errorFlag;  
   private char[] cards = {'A', '2', '3', '4', '5', '6', '7', 
         '8', '9', 'T', 'J', 'Q', 'K'};
   
  //Define the Suit enum, { clubs, diamonds, hearts, spades }, inside the Card class. 
  public enum Suit 
  {    
     CLUBS,
     SPADES,
     HEARTS,
     DIAMONDS  
  };
  
  /*Card(char value, Suit suit) - The constructor should call the proper mutator(s).  
  Overload this to cope with a client that wants to instantiate without parameters and use 'A' 
  and 'spades' as the default value and suit when not supplied.  Provide at least two constructors
   -- no parameters and all parameters -- or more if you wish.  
  Because we have the errorFlag member, the constuctor (via the mutator), can set that member 
  when it gets bad data; it does not have to assign default values upon receipt of bad data.  
  This is a new technique for us.  Again, default card (no parameters passed) is the ('A', spades).*/
  public Card(char value, Suit suit) 
  {
     set(value,suit);
  }
   
   //i would suggest using {set('A', Suit.SPADES);} to keep code consistance as our default construct
   public Card() 
   {
      this('A', Suit.SPADES);
   } 
  
  //copy constructor
  public Card(Card card){
    if(card == null){
      return;
    }
    this.value = card.value;
    this.suit = card.suit;
  }
  
   /*string toString() - a stringizer that the client can use prior to displaying the card.  
   It provides a clean representation of the card.  If errorFlag == true, 
   it should return correspondingly reasonable reflection of this fact (something like "
   [ invalid ]" rather than a suit and value). */
   public String toString()
   {
      if (errorFlag) 
      {
         return "**invalid**";
      } else
         return getValue() + " of " + getSuit();
   }
   /*a mutator that accepts the legal values established in the earlier section. 
   When bad values are passed, errorFlag is set to true and other values can be left in any state 
   (even partially set). 
   If good values are passed, they are stored and errorFlag is set to false. 
    Make use of the private helper, listed below.*/
   public boolean set(char value, Suit suit)
   {
      if (isValid(value,suit)) {
         this.value = Character.toUpperCase(value);
         this.suit = suit;
         errorFlag = false;
      } else {
         errorFlag = true;
      }     
     return errorFlag;
   } 
    public void setErrorFlag(boolean arg)
   {
      errorFlag = arg;
   }
   /*
    * This method returns the value for the card object.
    */
   public char getValue()
   {
      return this.value;
   }
   
   /*
    * This method returns the suit for the instance of the card. 
    */
   public Suit getSuit()
   {
      return this.suit;
   }

   public boolean getErrorFlag()
   {
      return errorFlag;
   }
   /*a private helper method that returns true or false, depending on the legality of the 
    * parameters.  
    Note that, although it may be impossible for suit to be illegal (due to its enum-ness), 
    we pass it, anyway, 
    in anticipation of possible changes to the type from enum to, say, char or int, someday. 
    We only need to test value, at this time.*/
    private boolean isValid(char value, Suit suit)
   {
     char upper = Character.toUpperCase(value);
     for(int i = 0; i < cards.length; i++) {
        if(upper == cards[i]) {
           return true;
        }
     }
     return false; 
   }
  //returns true if all the fields (members) are identical and false, otherwise.
  public boolean equals(Card card){
    if (card == null){
      return false;
    } 
    return (this.value == card.value && this.suit == card.suit);
  }
  
}

/*
 * One object of class Deck represents a deck of cards.
 */
class Deck
{
   public static final int MAX_CARDS = 312;
   private static Card[] masterPack;
   private Card[] cards;
   private int topCard;
   private int numPacks;
    
  /*
   * Constructor method for Class Deck.
   */
   public Deck(int numPacks)
   {
      
   allocateMasterPack();
   init(numPacks);
    
   }
  
  /*
   * Default Constructor method for Class Deck.
   */
  public Deck()
  {
    allocateMasterPack();
    init(numPacks = 1);
  }
  
  /*
   * This method repopulates the cards in the Cards private member.
   */
  public void init(int numPacks)
  {
    int total = numPacks * 52;// number of packs * 52
    cards = new Cards[total];// array initialized with totdal number of cards
    for (int i = 0; i < cards.length; i++)
    {
      cards[i] = masterPack[i% masterPack.length()];
    }
    
  }
  
  /*
   * This method generates the master pack for a deck of Card objects.
   */ 
  private static void allocateMasterPack()
  {
    // check if masterPack has already been generated.
     if (masterPack == null)
       return;
    
    masterPack = new Card[52];
    int count = 0;
    char[] values = {'T', 'J', 'Q', 'K', 'A'}
    
    for(char i = '2'; i <= '9'; i++)
    {
      for (Card.Suit suitType : Card.Suit.values())
         Card newCard = new Card(i, suitType);
         masterPack[count] = newCard;
         count++;
    }
    
    for (char value : values)
    {
      for (Card.Suit suitType : Card.Suit.values());
      {
        Card newCard = new Card(value, suitType);
        masterPack[count] = newCard;
        count++;
      }
    }
    
  }
  
  /*
   * This method mixes up the cards contained in the Deck object.
   */
   public void shuffle()
   {
      int index1;
      int index2;
      int num = cards.length;
     while( num > 0)
      {
       index1 = (int)(Math.random()* cards.length); 
       index2 = (int)(Math.random()* cards.length);  
        
        //swapping the elements 
        Card  = cards[index1]; 
        cards[index1] = cards[index2]; 
        cards[index1] = temp; 
        num--;
     }                  
   }
  
   /*
    * This method returns the Card object from the top of the deck.
    */
   public Card dealCard()
   {
      if (this.topCard <= 0)
        return null;
      Card returnCard = this.cards[this.topCard - 1];
      this.cards[this.topCard - 1] = null;
      this.topCard--;
      return returnCard;
   }
  
  /*
   * This method returns the value for the requested Card object in the Deck.
   */
  public Card inspectCard(int k)
  {
    
  }
   
  /*
   * This method returns the object value for topCard.
   */
   public int getTopCard()
   {
     return this.topCard;
   }

}

