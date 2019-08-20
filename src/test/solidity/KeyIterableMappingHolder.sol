pragma solidity ^0.5.7;

import "../../main/solidity/KeyIterableMapping.sol";

contract KeyIterableMappingHolder {
  using KeyIterableMapping for KeyIterableMapping.Store;

  KeyIterableMapping.Store private store;

  function put( uint256 key, uint256 value ) public returns ( bool replaced ) {
    replaced = store.put( key, value );
  }

  function remove( uint256 key ) public returns ( bool removed ) {
    removed = store.remove( key );
  }

  function get( uint256 key ) public view returns ( bool exists, uint256 value ) {
    ( exists, value ) = store.get( key );
  }

  function size() public view returns ( uint256 length ) {
    length = store.keyCount();
  }

  function keyAt( uint256 index ) public view returns ( uint256 key ) {
    key = store.keyAt( index );
  }
}
