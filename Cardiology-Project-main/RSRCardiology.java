// Approach is that of someone who ran a bunch of simulations for various
// values of r,c and came up with hypotheses about using just cards 1
// and r*c for testing, about always-viable values for p (namely, 1 and
// r*c), and about how cards make progress towards their final destination
// (namely, card 1 will always have a stable position that is (row-wise)
// less than or equal to card r*c's stable position).

import java.util.Scanner;

public class RSRCardiology 
{
  public static long rows;
  public static long cols;
  public static long MAX = 1000000;

  public static void main(String[] args)   
    {
    Scanner in = new Scanner(System.in);
    rows = in.nextLong();
    cols = in.nextLong();

    // It's not hard to see that p = 1 and p = r*c are always viable, i.e.,
    // always have a unique stable point: either (1,1) or (r,c).
    // Experimentation (and intuition) suggests that when there are two
    // or more stable points, cards 1 and r*c will end up in different places.

    // NOTE:  in what follows, we are using p to represent the number of
    // columns picked up BEFORE the target column, one less than the p
    // used in the problem statement.)

    long oldr,newr,oldc,newc;      // successive locations of chosen card
    long iter;                                 // number of iterations
    long bestdist;                          // distance of stable point from center
    long bestp;                             // smallest p for stable point nearest center
    long bestr, bestc;                   // stable point location
    long besti;                             // number of iterations needed

    // For p = 0, (1,1) is always the stable position; for
    // p = cols-1, (rows,cols) is always the stable position.
    // Both are the same distance from the center, so use
    // p = 0 to determine # iterations.

    iter = 0;
    oldr = oldc = -1;
    newr = rows; newc = cols;
    while (oldr != newr || oldc != newc) 
     {
      iter ++;
      oldr = newr; oldc = newc;
      newr =  (oldr-1)/cols+1;
      newc = (oldr-1)%cols+1;
       }
    besti = iter-1;
    bestp = 0;
    bestdist = dist(1,1);
    bestr = 1; bestc = 1;
    
    for (long p = 1; p < cols-1; p++) 
    {
      long it0, it1;                 // iterations for card 1 and card r*c (not sure if needed)
      // follow card 1; keep track of iterations needed:
      oldr = oldc = -1;
      newr = 1; newc = 1;     // location of card 1
      iter = 0;
      while (oldr != newr || oldc != newc)  
       {
        iter++;
        oldr = newr; oldc = newc;
        newr =  (p*rows+oldr-1)/cols+1;
        newc = (p*rows+oldr-1)%cols+1;
      }
      it0 = iter-1;
      long locr0 = newr;      // remember where card 1 ends up
      long locc0 = newc;

      // follow card rows*cols:
      oldr = oldc = -1;
      // Card rows*cols:
      newr = rows; newc = cols; // location of card rows*cols
      iter = 0;
      while (oldr != newr || oldc != newc) 
         {
        iter++;
        oldr = newr; oldc = newc;
        newr =  (p*rows+oldr-1)/cols+1;
        newc = (p*rows+oldr-1)%cols+1;
         }
      it1 = iter-1;
      if (locr0 != newr || locc0 != newc) 
     {
        continue; // multiple points
      }
      long d = dist(newr,newc);
      if (d < bestdist) 
    {
         bestdist = d;
         bestp = p;
         bestr = newr;
         bestc = newc;
         besti = Math.max(it0,it1);
      }
    }
    System.out.printf("%d %d %d %d\n",bestp+1,bestr, bestc, besti);
  }

  // distance from center of array:
  public static long dist(long a, long b)
     {
    long d = 2*MAX;
    for (int i = 1; i <= 2; i++) 
    {
      for (int j = 1; j <= 2; j++) 
     {
        long d1 = Math.abs(a-(rows+i)/2)+Math.abs(b-(cols+j)/2);
        if (d1 < d) d = d1;
      }
    }
    return d;
  }
}