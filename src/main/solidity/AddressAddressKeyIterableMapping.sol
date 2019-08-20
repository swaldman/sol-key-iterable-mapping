pragma solidity ^0.5.7; 

library AddressAddressKeyIterableMapping {
  struct Record {
    bool    exists; // this feels like a waste of storage...
    uint256 index;
    address value;
  }
  struct Store {
    mapping(address=>Record)   map;
    address[]                  keys;
  }
  function keyCount( Store storage store ) internal view returns ( uint256 count ) {
    count = store.keys.length;
  }
  function keyAt( Store storage store, uint256 index ) internal view returns ( address key ) {
    key = store.keys[index];
  }
  function allKeys( Store storage store ) internal view returns ( address[] memory keys ) {
    keys = store.keys;
  }
  function get( Store storage store, address key ) internal view returns ( bool exists, address value ) {
    Record storage rec = store.map[key];
    exists = rec.exists;
    value = rec.value;
  }
  function put( Store storage store, address key, address value ) internal returns ( bool replaced ) {
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
  function remove( Store storage store, address key ) internal returns ( bool deleted ) {
    if ( store.map[key].exists ) {
      removeFromArray( store, key ); // we have to change the array, can't just overwrite the fields
      delete store.map[key];
      deleted = true;
    }
    else {
      deleted = false;
    }
  }
  function removeFromArray( Store storage store, address key ) private {
    uint256 index = store.map[key].index;
    uint256 lastIndex = store.keys.length - 1;
    if ( index != lastIndex ) {
      require( store.keys[index] == key );
      address lastKey = store.keys[store.keys.length - 1];
      Record storage last = store.map[ lastKey ];
      store.keys[index] = lastKey;
      last.index = index;
    }
    store.keys.pop();
  }
}