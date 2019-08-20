pragma solidity ^0.5.7; 

library KeyIterableMapping {
  struct Record {
    bool    exists; // this feels like a waste of storage...
    uint256 index;
    uint256 value;
  }
  struct Store {
    mapping(uint256=>Record)   map;
    uint256[]                  keys;
  }
  function keyCount( Store storage store ) internal view returns ( uint256 count ) {
    count = store.keys.length;
  }
  function keyAt( Store storage store, uint256 index ) internal view returns ( uint256 key ) {
    key = store.keys[index];
  }
  function allKeys( Store storage store ) internal view returns ( uint256[] memory keys ) {
    keys = store.keys;
  }
  function get( Store storage store, uint256 key ) internal view returns ( bool exists, uint256 value ) {
    Record storage rec = store.map[key];
    exists = rec.exists;
    value = rec.value;
  }
  function put( Store storage store, uint256 key, uint256 value ) internal returns ( bool replaced ) {
    replaced = false;
    if ( store.map[key].exists ) {
      removeFromArray( store, key ); // we have to change the array, can't just overwrite the fields
      replaced = true;
    }
    store.keys.push( key );
    Record storage rec = store.map[key];  
    rec.exists = true;
    rec.index  = store.keys.length - 1;
    rec.value  = value;
  }
  function remove( Store storage store, uint256 key ) internal returns ( bool deleted ) {
    if ( store.map[key].exists ) {
      removeFromArray( store, key ); // we have to change the array, can't just overwrite the fields
      delete store.map[key];
      deleted = true;
    }
    else {
      deleted = false;
    }
  }
  function removeFromArray( Store storage store, uint256 key ) private {
    uint256 index = store.map[key].index;
    uint256 lastIndex = store.keys.length - 1;
    if ( index != lastIndex ) {
      require( store.keys[index] == key );
      uint256 lastKey = store.keys[store.keys.length - 1];
      Record storage last = store.map[ lastKey ];
      store.keys[index] = lastKey;
      last.index = index;
    }
    store.keys.pop();
  }
}