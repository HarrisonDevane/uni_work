package forsale.strategies;
import forsale.*;
import java.util.*;

// A random strategy - make a random bid up to your amount remaining,
// choose a rand card to sell.
public class MyStrategy implements Strategy {
    public int bid(PlayerRecord p, AuctionState a) {
        int tot_sum = 465; 
        int total_rounds = 30 /a.getPlayers().size();
        int rounds_left = a.getCardsInDeck().size()/a.getPlayers().size();
        int cur_round = total_rounds - rounds_left;
        int cash = p.getCash();
        int our_bid = p.getCurrentBid();
        int table_bid = a.getCurrentBid();
        List<Card> cards = a.getCardsInAuction();
        int av_val =0;
        int highest_val =0;
        
        /* betting first */
        if(table_bid ==0 && a.getCardsInAuction().size() >2){
            return 1;
        }
        if(our_bid <0){
            return -1;
        }
        if(a.getCardsInAuction().size()==1 ){
            return -1;
        }
        if(table_bid == our_bid && table_bid != 0){
            return -1;
        }
        
        
        /**calculations for finding average and highest value cards */
        for(int i =0; i < cards.size(); i++){
            Card card1 = cards.get(i);
            if (card1.getQuality() > highest_val){
                highest_val = card1.getQuality();
            }
            av_val = av_val + card1.getQuality();
        }
        av_val = av_val/cards.size();

        /* high bets - rewrite in terms of table bid  */
        if( highest_val >= 20){
            if(table_bid >=5 ){
                if(rounds_left >= 2){
                    if(cash > 7 && table_bid < 5){
                        return table_bid +1;
                    }else{
                        return -1;
                    }
                }else{
                    if(cash > 3 ){
                        return table_bid+1;
                    }
                    else{
                        return -1;
                    }
                }
            }else{
                return table_bid+1;
            }
        }

         /** mid bets  */
         if(highest_val < 20){
            if( table_bid > 4){
                return -1;
            }
            else{
                return table_bid +1;
            }
        }
        return -1;
    }

    public Card chooseCard(PlayerRecord p, SaleState s) {
        List<Card> player_cards = p.getCards(); 
        List<Integer> cheques = s.getChequesAvailable();
        int average_card_val =8;
        int difference =  cheques.get(cheques.size()-1) - cheques.get(0);
        int num_high =0;

        player_cards.sort((o1, o2) -> o1.getQuality() - o2.getQuality());
        for(int i =0; i < player_cards.size(); i++){
            System.out.println(player_cards.get(i).getQuality());
        }
        for(int i =0; i < player_cards.size(); i++){
            
        }
        int size = player_cards.size();
        if(cheques.contains(0)){
            /** play high card  */
            return player_cards.get(size-1);
        }

        if(difference <= 3){
            return player_cards.get(0);
        }
         /** add feature that if majority are double digit player a higher card  */
        if(num_high >= 2){
            if(player_cards.size() <=2 ){
                return player_cards.get(player_cards.size()-1);
            }else{
                return player_cards.get(player_cards.size()-2);
            }  
        }

        return player_cards.get((player_cards.size()-1)/2);
    }
}
