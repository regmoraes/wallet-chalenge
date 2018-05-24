## Crypto Wallet

Project for the [Stone's Mobile Challenge](https://github.com/stone-payments/desafio-mobile/blob/master/wallet/README.md)

### Screenshots

<p float="left">
<img width="40%" vspace="20" hspace="20" src="https://user-images.githubusercontent.com/4440882/40471021-c19f90aa-5f0b-11e8-8c6a-9c5c56863e52.png" />
<img width="40%" vspace="20" hspace="20" src="https://user-images.githubusercontent.com/4440882/40471025-c1e5c070-5f0b-11e8-81d2-07f436cada35.png" />
<img width="40%" vspace="20" hspace="20" src="https://user-images.githubusercontent.com/4440882/40471026-c2092574-5f0b-11e8-8823-7ea81a7d7e18.png" />
<img width="40%" vspace="20" hspace="20" src="https://user-images.githubusercontent.com/4440882/40471023-c1c5b12c-5f0b-11e8-840a-0700f232d39e.png" />
</p>

### Project Structure

The application was created using the Kotlin language and sought to follow the concepts and practices of:

- S.O.L.I.D
- [Component Based Architecture](https://www.tutorialspoint.com/software_architecture_design/component_based_architecture.htm)
- MVVM (Model-View-ViewModel)
- Reactive Programming
 
The project was divided into 3 modules, each one responsible for a software layer. The dependencies 
are unidirectional and non-transitive, from 1 to 3:
 
1. **app:** Android application responsible for accessing and manipulating Android classes and to provide a presentation layer 
that follows the MVVM pattern.

2. **wallet-core:** Java library responsible for providing methods and classes to manage transactions, wallets, and to query, purchase, sale, and exchange cryptocurrencies.

3. **wallet-api:** Java library responsible for providing methods and wrappers to query cryptocurrencies info provided by cryptocurrencies APIs.

The *wallet-core* module (along with its *wallet-api* dependency) was created to act as a component that
provides classes responsible for accessing and managing data from a specific context (wallet, market, transaction etc.). To provide this structure, 
the *wallet-core* provides a **WalletCoreCreator** class, which provides the implementation of a **WalletCore** interface, 
which in turn has methods that return classes that operate on a defined context; These classes are called _Managers_.

To get an implementation of **WalletCore** you need to provide some repository interface implementations to the **WalletCoreCreator**.

Because the *app* module depends on the *wallet-core*, the above structure looks like this:

![wallet-dependencies](https://user-images.githubusercontent.com/4440882/40459969-c1545404-5eda-11e8-92d2-50e87419e579.png)
 
 ### Dependencies Used (dependencies.gradle):
 
- [Dagger2](https://google.github.io/dagger/)
- Reactive Extensions ([RxJava2](https://github.com/ReactiveX/RxJava), [RxAndroid](https://github.com/ReactiveX/RxAndroid))
- [Retrofit](http://square.github.io/retrofit/)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/) (Room, ViewModel)
- [Mockito](http://site.mockito.org/)
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)
- [Espresso](https://developer.android.com/training/testing/espresso/)
- (...)

### Tests

Unit and instrumental tests were implemented. The tests were intended to deal with the majority of success and error scenarios of use cases of the following elements:

- Managers from wallet-core
- Concrete APIs implementations from wallet-api
- Transaction history, wallet and market Fragments

### Notes

1. By default all classes in Kotlin are *final*, however, this ends up [generating problems](https://github.com/mockito/mockito/issues/1082) to mock classes
in instrumentation tests. There are a few ways to work around this problem, but due to the time, it was decided
simply to change the visibility of some classes and methods to *open*.

2. No layouts were generated for bigger resolutions and some styles and resourceswere declared directly in layouts, not on its own files.
