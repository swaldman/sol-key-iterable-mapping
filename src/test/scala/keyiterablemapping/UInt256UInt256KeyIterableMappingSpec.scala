package keyiterablemapping

import org.specs2._

import com.mchange.sc.v1.consuela.ethereum.stub
import com.mchange.sc.v1.consuela.ethereum.stub.sol

import keyiterablemapping.contract._
import Testing._

class UInt256UInt256KeyIterableMappingSpec extends Specification with AutoSender { def is = sequential ^ s2"""
   A KeyIterableMapping...
      starts out empty                                      ${e0}
      after one put has size one                            ${e1}
      key exists and value is as expected                   ${e1_1} 
      bad key does exists and value is zero                 ${e1_2} 
      after one replace has size one                        ${e2}
      after replacement value is replaced                   ${e2_2}
      after another put has size two                        ${e3}
      has two expected keys                                 ${e4}
      after removing one good and one bad key size is one   ${e5}
      after removing the last good key size is zero         ${e6}
"""

  val ZERO  = sol.UInt256( 0)
  val ONE   = sol.UInt256( 1)
  val TWO   = sol.UInt256( 2)
  val THREE = sol.UInt256( 3)
  val FOUR  = sol.UInt256( 4)
  val FIVE  = sol.UInt256( 5)
  val SIX   = sol.UInt256( 6)
  val SEVEN = sol.UInt256( 7)
  val EIGHT = sol.UInt256( 8)
  val NINE  = sol.UInt256( 9)
  val TEN   = sol.UInt256(10)

  val Holder = KeyIterableMappingHolder( TestSender(0).contractAddress(0) )

  def e0 = Holder.view.size() == ZERO
  def e1 = {
    Holder.txn.put( ONE, ONE )
    Holder.view.size() == ONE
  }
  def e1_1 = {
    val ( exists, value ) = Holder.view.get( ONE )
    exists && value == ONE
  }
  def e1_2 = {
    val ( exists, value ) = Holder.view.get( THREE )
    (!exists) && value == ZERO
  }
  def e2 = {
    Holder.txn.put( ONE, TWO )
    Holder.view.size() == ONE
  }
  def e2_2 = {
    val ( exists, value ) = Holder.view.get( ONE )
    exists && value == TWO
  }
  def e3 = {
    Holder.txn.put( TWO, TWO )
    Holder.view.size() == TWO
  }
  def e4 = {
    Set( Holder.view.keyAt( ZERO ), Holder.view.keyAt( ONE ) ) == Set( ONE, TWO )
  }
  def e5 = {
    Holder.txn.remove( ONE )
    Holder.txn.remove( THREE )
    Holder.view.size() == ONE
  }
  def e6 = {
    Holder.txn.remove( TWO )
    Holder.view.size() == ZERO
  }
}
