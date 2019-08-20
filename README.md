# Generate KeyIterableMappings

In solidity it is frequent to want parallel data structures, a mapping of keys and values and an
array to track and render iterable the defined keys.

This package defines a library for doing that.

Unfortunately, you would like such a library to be defined in terms of generic types. Solidity does
not yet support generic types. So this build includes a solidity task to generate specializations of
the library for specific types. They get generated (perhaps awkwardly) into `src/main/solidity`.

### Examples

This command generated `AddressAddressKeyIterableMapping`:

```
> generateKeyIterableMapping address address
[success] Total time: 0 s, completed Aug 19, 2019 10:12:07 PM
```


Note that the default heuristic, if you supply only two arguments to the task, is to name
the specialization `<capitalized-key-type><capitalized-value-type>KeyIterableMapping`.

However, if that's not great, you can define the library name directly by providing a third
argument to `generateKeyIterableMapping`. This command generated `UInt256UInt256KeyIterableMapping`:

```
> generateKeyIterableMapping uint256 uint256 UInt256UInt256KeyIterableMapping
[success] Total time: 0 s, completed Aug 19, 2019 10:10:12 PM
```

### Template

The template file, if you want to fill it out directly, is [here](project/src/main/resources/template.sol).

### Modifications / Apologies

If the template is modified, you'll have to regenerate all the solidity specializations by hand.

The current tests ony test the prototype file `KeyIterableMapping.sol`. To regenerate that from an updated template, try

```
> generateKeyIterableMapping uint256 uint256 KeyIterableMapping
[success] Total time: 0 s, completed Aug 19, 2019 10:10:12 PM
```

