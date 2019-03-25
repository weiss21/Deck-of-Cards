import java.util.Scanner;

public class Assign3
{   
   public static void main(String[] args)
   { 
      // Phase One Test
      //test for card to see if illegal or not
      System.out.println("Begin Phase One Test:");
      Card legal1 = new Card();
      Card legal2 = new Card('J', Card.Suit.CLUBS);
      Card illegal = new Card('Y', Card.Suit.SPADES);
   
      //test to string
      System.out.println(legal1);
      System.out.println(illegal);
      System.out.println(legal2);
      System.out.println();
     
      //testing set boolean
      legal1.set('Y', Card.Suit.SPADES);
      illegal.set('Q', Card.Suit.SPADES);
      System.out.println(legal1);
      System.out.println(illegal);
      System.out.println(legal2);
     
      // Phase Two Test
      System.out.println("\nBegin Phase Two Test");
      Hand testHand = new Hand();
     
      // instantiate test cards and load into an array
      Card testCard1 = new Card('A', Card.Suit.DIAMONDS);
      Card testCard2 = new Card('5', Card.Suit.SPADES);
      Card testCard3 = new Card('K', Card.Suit.HEARTS);
      Card testCard4 = new Card('2', Card.Suit.CLUBS);
      Card testCard5 = new Card('Q', Card.Suit.DIAMONDS);
      Card[] testCards = {testCard1, testCard2, testCard3, testCard4, 
        testCard5};

      // Fill hand with cards and print result
      for (int i = 0; i < Hand.MAX_CARDS; i++)
      {
         int randomizer = (int)(Math.random() * 5);
         testHand.takeCard(testCards[randomizer]);
      }
      System.out.println("Hand Full\nHand = " + testHand.toString() + "\n");
     
      //Test inspectCard(). Call a legal and illegal card.
      System.out.print("Testing with k value 0: " );
      System.out.println(testHand.inspectCard(0));
      System.out.print("Testing with k value 55: ");
      System.out.println(testHand.inspectCard(55));
     
      // Test playCard() method. Play every card in testHand.
      System.out.print("\nTesting playCard(): ");
      
      while (testHand.getNumCards() > 0) 
      {
         System.out.println("Playing: " + testHand.playCard());
      }
      System.out.print("\nPrinting Hand Contents (Should Be Empty): ");
      System.out.println(testHand.toString());

      // Phase Three Test
      System.out.println("\nBegin Phase Three Test:");
      Deck deck = new Deck(2); // test deck with 2 pack
      Deck deck2 = new Deck(); // test deck with default

      System.out.println("Test with 2 pack deck:");

      while (true)
      {
         if (deck.getTopCard() <= 0)
            break;
         System.out.print(deck.dealCard().toString() + " / ");
      }      
      deck = new Deck(2);
      deck.shuffle();
      System.out.println("\n\nShuffle two packs deck.\n");
      
      while(true) 
      {
         if (deck.getTopCard() <= 0) 
            break;
         System.out.print(deck.dealCard().toString() + " / ");
      }
      
      // Test a single pack with shuffle()
      System.out.println("\n\nTest with 1 pack deck\n");
      
      while (true)
      {
         if (deck2.getTopCard() <= 0)
            break;
         System.out.print(deck2.dealCard().toString() + " / ");
      }
      deck2 = new Deck();
      deck2.shuffle();
      System.out.println("\n\nShuffle one packs deck.\n");
      while(true) 
      {
         if(deck2.getTopCard() <= 0)
            break;
         System.out.print(deck2.dealCard().toString() + " / ");
      }
      System.out.println("End Phase Three Test.\n");

      // Phase Four Test
      int numPlayers = 0;
      int dealtPlayer = 0;
      Scanner keyboard = new Scanner(System.in);
      
      // receive user player count and validate
      System.out.println("Begin Phase Four Test: ");
      do
      {
      System.out.println("Enter in the number of players "
         + "(between 1 and 10): ");
      numPlayers = keyboard.nextInt();
      } while (numPlayers <= 0 || numPlayers > 10);
      
      // create players
      Hand[] players = new Hand[numPlayers];
      for(int i = 0; i < numPlayers; i++)
      {
         Hand newPlayer =  new Hand();
         players[i] = newPlayer;
      }
      Deck gameDeck = new Deck();     
      // deal cards out until the deck is empty
      while (gameDeck.getTopCard() > 0)
      {
         if (dealtPlayer == numPlayers)
            dealtPlayer = 0;
         players[dealtPlayer].takeCard(gameDeck.dealCard());
         dealtPlayer++;
      }
      // print each player hands
      int playerCounter = 1;
      System.out.println("Printing out unshuffled player hands: ");
      for (Hand player : players)
      {
         System.out.println("Player " + playerCounter + ": " 
            + player.toString());
         playerCounter++;
      }
      // reset hands and shuffle deck
      System.out.println("\nResetting Deck and Shuffling: ");
      gameDeck = new Deck(); 
      gameDeck.shuffle();
      for (Hand player : players)
         player.resetHand();

      // deal shuffled cards
      dealtPlayer = 0;
      
      while (gameDeck.getTopCard() > 0)
      {
         if (dealtPlayer == numPlayers)
            dealtPlayer = 0;
         players[dealtPlayer].takeCard(gameDeck.dealCard());
         dealtPlayer++;
      }

      // print each player hands
      playerCounter = 1;
      System.out.println("Printing out shuffled player hands: ");
      for (Hand player : players)
      {
         System.out.println("Player " + playerCounter + ": " 
            + player.toString());
         playerCounter++;
      }
      System.out.println("Testing Complete.");
      keyboard.close();
   }
}

/*
 * One object of class Card represents an individual card from a standard deck
 * of playing cards.
 */
class Card
{   
   private char value;
   private Suit suit;
   private boolean errorFlag;
   private char[] cards = {'A', '2', '3', '4', '5', '6', '7', 
      '8', '9', 'T', 'J', 'Q', 'K'};   

   public enum Suit 
   {    
      CLUBS,
      SPADES,
      HEARTS,
      DIAMONDS  
   }; 

   /*
    * Constructor method for class Card. This method receives a value and suit
    * and creates a Card object with those attributes.
    */
   public Card(char value, Suit suit) 
   {
      set(value,suit);
   }   

   /*
    * Default constructor for class Card. Creates a Card that represents the
    * ace of spades.
    */
   public Card() 
   {
      this('A', Suit.SPADES);
   }  
  
   /*
    * Copy Constructor for class Card. This method receives a Card object, and 
    * generates another one containing identical data.
    */
   public Card(Card card)
   {
      if(card == null)
         return;
      this.value = card.value;
      this.suit = card.suit;
   }

   /*
    * This method returns a string representation of the Card object.
    */
   public String toString()
   {
      if (errorFlag) 
      {
         return "**invalid**";
      } 
      return getValue() + " of " + getSuit();
   }

   /*
    * Mutator method that sets the numerical value and suit for the Card 
    * object.
    */
   public boolean set(char value, Suit suit)
   {
      if (isValid(value,suit)) 
      {
         this.value = Character.toUpperCase(value);
         this.suit = suit;
         errorFlag = false;
      } else 
      {
         errorFlag = true;
      }   
      return errorFlag;
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

   /*
    * Accessor method that returns the Card object's error flag/
    */
   public boolean getErrorFlag()
   {
      return this.errorFlag;
   }

   /*
    * This method receives a character value and determines if it is suitable
    * for use as an attribute for the Card value. The method returns true if 
    * the character can be used.
    */
   private boolean isValid(char value, Suit suit)
   {
      char upper = Character.toUpperCase(value);
      for(int i = 0; i < cards.length; i++) 
      {
         if(upper == cards[i]) 
         {
            return true;
         }
      }
      return false; 
   }

  /*
   * This method receives a Card object and determines if it is equal in value
   * to the receiving Card object. A boolean is returned true if both objects
   * contain the same data.
   */
   public boolean equals(Card card)
   {
      if (card == null)
      {
         return false;
      } 
      return (this.value == card.value && this.suit == card.suit);
   }
}

/*
 * One object of class Hand represents a hand in a card game. It is composed of 
 * Card objects.
 */ 
class Hand
{
   public static final int MAX_CARDS = 52;
   private Card[] myCards;
   private int numCards;

   /*
    * This method is the default constructor method for class Hand.
    */
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
      for(int i = 0; i < numCards; i++)
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
            returnString += this.myCards[i].toString() + " )";
         }
         else
            returnString += this.myCards[i].toString() + ", ";
      }
      return returnString;
   }

   /*
    * This method receives an integer value and locates the associated Card in
    * the Hand. If the Card is not located, a new Card is generated and 
    * returned.
    */
   public Card inspectCard(int k)
   {
      if (k >= 0 && k < numCards)
         return myCards[k];
      else
         return new Card('x', Card.Suit.SPADES);
   }
}

/*
 * One object of class Deck represents a standard deck of playing cards.
 */
class Deck
{
   public static final int MAX_CARDS = 312;
   private static Card[] masterPack;
   private Card[] cards;
   private int topCard;

   /*
    * Constructor method for Class Deck. This method receives an integer 
    * numPacks and generates a deck with numPacks number of card packs.
    */
   public Deck(int numPacks)
   {
      allocateMasterPack();
      init(numPacks);    
   }  
  
   /*
    * Default Constructor method for Class Deck. This method creates a Deck
    * with one pack of Card objects.
    */
   public Deck()
   {
      this(1);
   }
  
   /*
    * This method repopulates the cards in the Cards private member.
    */
   public void init(int numPacks)
   {
      int packLimit = (MAX_CARDS / 52);
      // array initialized with total number of cards
      if (numPacks > 0 && numPacks <= packLimit)
      {
         int total = numPacks * 52;
         cards = new Card[total];
         for (int i = 0; i < cards.length; i++)
         {
            cards[i] = new Card(masterPack[i % masterPack.length]);
         }
         this.topCard = total; // initialize topCard with the total amount
      }
   }

   /*
    * This method generates the master pack for a deck of Card objects.
    */ 
   private static void allocateMasterPack()
   {
   // check if masterPack has already been generated.
      if (masterPack != null)
         return;
    
      masterPack = new Card[52];
      int count = 0;
      char[] values = {'T', 'J', 'Q', 'K', 'A'};
   
      // make all the numbered cards
      for(char i = '2'; i <= '9'; i++)
      {
         for (Card.Suit suitType : Card.Suit.values())
         {
            Card newCard = new Card(i, suitType);
            masterPack[count] = newCard;
            count++;
         }
      }
   
      // make all the face cards
      for (char value : values)
      {
         for (Card.Suit suitType : Card.Suit.values())
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
         Card temp = cards[index1]; 
         cards[index1] = cards[index2]; 
         cards[index2] = temp; 
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
      if (k >= 0 && k < cards.length)
         return cards[k];
      else
         return new Card('x', Card.Suit.SPADES);
   }

   /*
    * This method returns the object value for topCard.
    */
   public int getTopCard()
   {
      return this.topCard;
   }
}

/********************* OUTPUT **************************************************
Begin Phase One Test:
A of SPADES
**invalid**
J of CLUBS

**invalid**
Q of SPADES
J of CLUBS

Begin Phase Two Test
Hand Full
Hand = (Q of DIAMONDS, K of HEARTS, 2 of CLUBS, 5 of SPADES, 2 of CLUBS, Q of DI
AMONDS, K of HEARTS, 5 of SPADES, 5 of SPADES, 2 of CLUBS, A of DIAMONDS, 5 of S
PADES, 2 of CLUBS, Q of DIAMONDS, 2 of CLUBS, 2 of CLUBS, K of HEARTS, 2 of CLUB
S, Q of DIAMONDS, A of DIAMONDS, K of HEARTS, K of HEARTS, 2 of CLUBS, K of HEAR
TS, A of DIAMONDS, Q of DIAMONDS, 2 of CLUBS, 5 of SPADES, Q of DIAMONDS, 5 of S
PADES, 5 of SPADES, K of HEARTS, Q of DIAMONDS, 2 of CLUBS, 5 of SPADES, K of HE
ARTS, K of HEARTS, 2 of CLUBS, 2 of CLUBS, K of HEARTS, Q of DIAMONDS, Q of DIAM
ONDS, 2 of CLUBS, 5 of SPADES, 2 of CLUBS, 2 of CLUBS, 2 of CLUBS, K of HEARTS, 
Q of DIAMONDS, 5 of SPADES, 5 of SPADES, 2 of CLUBS )

Testing with k value 0: Q of DIAMONDS
Testing with k value 55: **invalid**

Testing playCard(): Playing: 2 of CLUBS
Playing: 5 of SPADES
Playing: 5 of SPADES
Playing: Q of DIAMONDS
Playing: K of HEARTS
Playing: 2 of CLUBS
Playing: 2 of CLUBS
Playing: 2 of CLUBS
Playing: 5 of SPADES
Playing: 2 of CLUBS
Playing: Q of DIAMONDS
Playing: Q of DIAMONDS
Playing: K of HEARTS
Playing: 2 of CLUBS
Playing: 2 of CLUBS
Playing: K of HEARTS
Playing: K of HEARTS
Playing: 5 of SPADES
Playing: 2 of CLUBS
Playing: Q of DIAMONDS
Playing: K of HEARTS
Playing: 5 of SPADES
Playing: 5 of SPADES
Playing: Q of DIAMONDS
Playing: 5 of SPADES
Playing: 2 of CLUBS
Playing: Q of DIAMONDS
Playing: A of DIAMONDS
Playing: K of HEARTS
Playing: 2 of CLUBS
Playing: K of HEARTS
Playing: K of HEARTS
Playing: A of DIAMONDS
Playing: Q of DIAMONDS
Playing: 2 of CLUBS
Playing: K of HEARTS
Playing: 2 of CLUBS
Playing: 2 of CLUBS
Playing: Q of DIAMONDS
Playing: 2 of CLUBS
Playing: 5 of SPADES
Playing: A of DIAMONDS
Playing: 2 of CLUBS
Playing: 5 of SPADES
Playing: 5 of SPADES
Playing: K of HEARTS
Playing: Q of DIAMONDS
Playing: 2 of CLUBS
Playing: 5 of SPADES
Playing: 2 of CLUBS
Playing: K of HEARTS
Playing: Q of DIAMONDS

Printing Hand Contents (Should Be Empty): (

Begin Phase Three Test:
Test with 2 pack deck:
A of DIAMONDS / A of HEARTS / A of SPADES / A of CLUBS / K of DIAMONDS / K of HE
ARTS / K of SPADES / K of CLUBS / Q of DIAMONDS / Q of HEARTS / Q of SPADES / Q 
of CLUBS / J of DIAMONDS / J of HEARTS / J of SPADES / J of CLUBS / T of DIAMOND
S / T of HEARTS / T of SPADES / T of CLUBS / 9 of DIAMONDS / 9 of HEARTS / 9 of 
SPADES / 9 of CLUBS / 8 of DIAMONDS / 8 of HEARTS / 8 of SPADES / 8 of CLUBS / 7
 of DIAMONDS / 7 of HEARTS / 7 of SPADES / 7 of CLUBS / 6 of DIAMONDS / 6 of HEA
RTS / 6 of SPADES / 6 of CLUBS / 5 of DIAMONDS / 5 of HEARTS / 5 of SPADES / 5 o
f CLUBS / 4 of DIAMONDS / 4 of HEARTS / 4 of SPADES / 4 of CLUBS / 3 of DIAMONDS
 / 3 of HEARTS / 3 of SPADES / 3 of CLUBS / 2 of DIAMONDS / 2 of HEARTS / 2 of S
PADES / 2 of CLUBS / A of DIAMONDS / A of HEARTS / A of SPADES / A of CLUBS / K 
of DIAMONDS / K of HEARTS / K of SPADES / K of CLUBS / Q of DIAMONDS / Q of HEAR
TS / Q of SPADES / Q of CLUBS / J of DIAMONDS / J of HEARTS / J of SPADES / J of
 CLUBS / T of DIAMONDS / T of HEARTS / T of SPADES / T of CLUBS / 9 of DIAMONDS 
/ 9 of HEARTS / 9 of SPADES / 9 of CLUBS / 8 of DIAMONDS / 8 of HEARTS / 8 of SP
ADES / 8 of CLUBS / 7 of DIAMONDS / 7 of HEARTS / 7 of SPADES / 7 of CLUBS / 6 o
f DIAMONDS / 6 of HEARTS / 6 of SPADES / 6 of CLUBS / 5 of DIAMONDS / 5 of HEART
S / 5 of SPADES / 5 of CLUBS / 4 of DIAMONDS / 4 of HEARTS / 4 of SPADES / 4 of 
CLUBS / 3 of DIAMONDS / 3 of HEARTS / 3 of SPADES / 3 of CLUBS / 2 of DIAMONDS /
 2 of HEARTS / 2 of SPADES / 2 of CLUBS / 

Shuffle two packs deck.

A of DIAMONDS / 8 of HEARTS / K of DIAMONDS / 6 of HEARTS / 2 of SPADES / A of C
LUBS / T of SPADES / T of DIAMONDS / 9 of CLUBS / K of HEARTS / 4 of DIAMONDS / 
A of CLUBS / 3 of SPADES / J of SPADES / J of SPADES / 6 of DIAMONDS / 9 of DIAM
ONDS / J of CLUBS / Q of DIAMONDS / 5 of HEARTS / 9 of CLUBS / 5 of CLUBS / 2 of
 SPADES / J of HEARTS / 8 of DIAMONDS / 7 of SPADES / Q of SPADES / 7 of DIAMOND
S / 9 of SPADES / 9 of HEARTS / 9 of SPADES / 7 of CLUBS / 3 of HEARTS / 4 of DI
AMONDS / A of HEARTS / 5 of DIAMONDS / 3 of CLUBS / 5 of HEARTS / 5 of SPADES / 
3 of CLUBS / 7 of HEARTS / K of DIAMONDS / 3 of HEARTS / 4 of HEARTS / 6 of DIAM
ONDS / 8 of HEARTS / K of SPADES / 2 of CLUBS / 3 of DIAMONDS / K of SPADES / 5 
of SPADES / Q of HEARTS / 2 of HEARTS / A of SPADES / 2 of DIAMONDS / 7 of CLUBS
 / 8 of CLUBS / J of DIAMONDS / 8 of SPADES / K of CLUBS / T of SPADES / 3 of SP
ADES / Q of CLUBS / 4 of HEARTS / T of DIAMONDS / 2 of HEARTS / Q of DIAMONDS / 
A of SPADES / J of DIAMONDS / T of HEARTS / 9 of HEARTS / A of DIAMONDS / 2 of D
IAMONDS / 8 of CLUBS / 9 of DIAMONDS / 6 of SPADES / 8 of DIAMONDS / 4 of CLUBS 
/ 4 of CLUBS / T of HEARTS / 7 of DIAMONDS / 7 of HEARTS / K of CLUBS / 8 of SPA
DES / T of CLUBS / 2 of CLUBS / 6 of SPADES / 6 of CLUBS / K of HEARTS / 7 of SP
ADES / J of CLUBS / 5 of DIAMONDS / 5 of CLUBS / Q of CLUBS / 4 of SPADES / A of
 HEARTS / 3 of DIAMONDS / T of CLUBS / Q of HEARTS / J of HEARTS / 4 of SPADES /
 6 of CLUBS / Q of SPADES / 6 of HEARTS / 

Test with 1 pack deck

A of DIAMONDS / A of HEARTS / A of SPADES / A of CLUBS / K of DIAMONDS / K of HE
ARTS / K of SPADES / K of CLUBS / Q of DIAMONDS / Q of HEARTS / Q of SPADES / Q 
of CLUBS / J of DIAMONDS / J of HEARTS / J of SPADES / J of CLUBS / T of DIAMOND
S / T of HEARTS / T of SPADES / T of CLUBS / 9 of DIAMONDS / 9 of HEARTS / 9 of 
SPADES / 9 of CLUBS / 8 of DIAMONDS / 8 of HEARTS / 8 of SPADES / 8 of CLUBS / 7
 of DIAMONDS / 7 of HEARTS / 7 of SPADES / 7 of CLUBS / 6 of DIAMONDS / 6 of HEA
RTS / 6 of SPADES / 6 of CLUBS / 5 of DIAMONDS / 5 of HEARTS / 5 of SPADES / 5 o
f CLUBS / 4 of DIAMONDS / 4 of HEARTS / 4 of SPADES / 4 of CLUBS / 3 of DIAMONDS
 / 3 of HEARTS / 3 of SPADES / 3 of CLUBS / 2 of DIAMONDS / 2 of HEARTS / 2 of S
PADES / 2 of CLUBS / 

Shuffle one packs deck.

5 of CLUBS / 5 of DIAMONDS / J of CLUBS / 6 of HEARTS / K of DIAMONDS / 5 of HEA
RTS / T of HEARTS / 8 of SPADES / A of HEARTS / 4 of SPADES / 4 of DIAMONDS / 9 
of CLUBS / 4 of CLUBS / J of HEARTS / Q of HEARTS / 8 of CLUBS / 2 of HEARTS / K
 of SPADES / 7 of CLUBS / 5 of SPADES / Q of DIAMONDS / A of DIAMONDS / 6 of DIA
MONDS / J of SPADES / 3 of SPADES / 9 of DIAMONDS / J of DIAMONDS / 7 of DIAMOND
S / 7 of HEARTS / Q of CLUBS / T of SPADES / 4 of HEARTS / 9 of SPADES / 3 of DI
AMONDS / 8 of DIAMONDS / 8 of HEARTS / K of HEARTS / 6 of CLUBS / Q of SPADES / 
K of CLUBS / 2 of CLUBS / 9 of HEARTS / 3 of HEARTS / T of CLUBS / A of CLUBS / 
7 of SPADES / 6 of SPADES / 3 of CLUBS / 2 of DIAMONDS / T of DIAMONDS / 2 of SP
ADES / A of SPADES / End Phase Three Test.

Begin Phase Four Test: 
Enter in the number of players (between 1 and 10): 
3
Printing out unshuffled player hands: 
Player 1: (A of DIAMONDS, A of CLUBS, K of SPADES, Q of HEARTS, J of DIAMONDS, J
 of CLUBS, T of SPADES, 9 of HEARTS, 8 of DIAMONDS, 8 of CLUBS, 7 of SPADES, 6 o
f HEARTS, 5 of DIAMONDS, 5 of CLUBS, 4 of SPADES, 3 of HEARTS, 2 of DIAMONDS, 2 
of CLUBS )
Player 2: (A of HEARTS, K of DIAMONDS, K of CLUBS, Q of SPADES, J of HEARTS, T o
f DIAMONDS, T of CLUBS, 9 of SPADES, 8 of HEARTS, 7 of DIAMONDS, 7 of CLUBS, 6 o
f SPADES, 5 of HEARTS, 4 of DIAMONDS, 4 of CLUBS, 3 of SPADES, 2 of HEARTS )
Player 3: (A of SPADES, K of HEARTS, Q of DIAMONDS, Q of CLUBS, J of SPADES, T o
f HEARTS, 9 of DIAMONDS, 9 of CLUBS, 8 of SPADES, 7 of HEARTS, 6 of DIAMONDS, 6 
of CLUBS, 5 of SPADES, 4 of HEARTS, 3 of DIAMONDS, 3 of CLUBS, 2 of SPADES )

Resetting Deck and Shuffling: 
Printing out shuffled player hands: 
Player 1: (3 of SPADES, A of CLUBS, K of SPADES, 2 of CLUBS, Q of SPADES, Q of H
EARTS, A of DIAMONDS, 6 of CLUBS, 4 of DIAMONDS, K of HEARTS, 8 of HEARTS, J of 
SPADES, 5 of HEARTS, J of HEARTS, T of CLUBS, 7 of CLUBS, 7 of DIAMONDS, 5 of DI
AMONDS )
Player 2: (J of DIAMONDS, 6 of DIAMONDS, K of CLUBS, 3 of CLUBS, 7 of HEARTS, T 
of DIAMONDS, K of DIAMONDS, T of SPADES, Q of CLUBS, T of HEARTS, 2 of DIAMONDS,
 6 of SPADES, 2 of SPADES, 8 of DIAMONDS, 4 of CLUBS, 9 of HEARTS, 4 of SPADES )
Player 3: (Q of DIAMONDS, 5 of CLUBS, 6 of HEARTS, 4 of HEARTS, 8 of CLUBS, 9 of
 DIAMONDS, 2 of HEARTS, J of CLUBS, 3 of HEARTS, 3 of DIAMONDS, A of HEARTS, 9 o
f SPADES, 5 of SPADES, 7 of SPADES, 9 of CLUBS, 8 of SPADES, A of SPADES )
Testing Complete.

*******************************************************************************/
