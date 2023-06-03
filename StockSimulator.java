import java.util.LinkedList;
import java.util.Queue;
import java.util.Map;
import java.util.TreeMap;

/**
 * Title:       StockSimulator.java
 * Semester:    COP3337-Summer2022
 * @author:     Dianelys Rocha
 *
 * 
 */

/**
   Class for simulating trading a single stock at varying prices.
*/
public class StockSimulator
{
   private Map<String, Queue<Block>> blocks;

   /**
      Constructor.
   */
   public StockSimulator()
   {

       blocks = new TreeMap<>();

   }

   /**
      Handle a user buying a given quantity of stock at a given price.
      @param symbol the stock to buy
      @param quantity how many to buy.
      @param price the price to buy.
   */
   public void buy(String symbol, int quantity, int price)
  {
      symbol = symbol.toLowerCase();
      Block itemToBuy= new Block (quantity, price);

      if(blocks.containsKey(symbol))
      {   //checks if key already in map
          blocks.get(symbol).add(itemToBuy);
      }
      else
      {   //adds the new key to map
          Queue <Block> buyQ = new LinkedList<>();
          buyQ.add(itemToBuy);
          blocks.put(symbol, buyQ);
      }
  }

  /**
     Handle a user selling a given quantity of stock at a given price.
     @param symbol the stock to sell
     @param quantity how many to sell.
     @param price the price to sell.
  */
  public void sell(String symbol, int quantity, int price)
  {
      symbol = symbol.toLowerCase();
      double gain=0;
      Queue <Block> sellQ;

      if(blocks.containsKey(symbol))
      {
          sellQ = blocks.get(symbol);
          Block blockToSell;
          while (quantity > 0 && !sellQ.isEmpty())
          {   //while I have items to sell
              blockToSell = sellQ.peek();

              if(quantity > sellQ.peek().getQuantity() )
              {
                  //if quantity to  > than block quantity, sell all items in the block
                  //sellPrice * original qty - buyPrice*original qty
                  gain+= price*blockToSell.getQuantity()-blockToSell.getPrice()*blockToSell.getQuantity();
                  quantity -= blockToSell.getQuantity();
                  blockToSell.sell(blockToSell.getQuantity());
                  sellQ.poll();
              }
              else
              {
                  //if quantity to sell < block quantity, sell the quantity;
                  //gain sellPrice * sell Qty - buyPrice* buyQty;
                  gain+= price*quantity-blockToSell.getPrice()*quantity;
                  blockToSell.sell(quantity);
                  quantity -=quantity;
              }
          }
          System.out.println(" >> Transaction Gain: $"+gain);
      }
  }
}
