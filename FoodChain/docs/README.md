Kishkinova Olha, Rybina Alina

# Food Chain Project

## Description

## UML class diagram

![Use case diagram](/docs/..)

## Use case diagram

![Use case diagram](/docs/..)

## Functional requirements

- **F1** <br/>
  Main entities are located in the "core/party" package(exception: Product is located in the "core/product" package).
- **F2** <br/>
  Transition of a product is happening in the *publishPartyEvent* function, by looking for a customer, creating a
  payment and transaction and passing the product to the next party. Payment for the provided operation and product is
  processed in the *processPayment* function.
  Transactions are created in the *createTransaction* function. Mentioned functions are located in the *Channel* class.
  There are 5 operations with a number of parameters, located in the *core/operation* package.
- **F3** <br/>
  Transaction stores information about the product, party and conducted payment, from what hash is generated.
  Transaction class is located in the  *core/transaction* package. Transaction hash validity is checked by comparing
  generated hash to the stored one every time transaction is generated. List of conducted transactions is stored in a
  channel.
- **F4** <br/>
  During the entire life cycle, the product retains a history of actions performed with it, which can be downloaded by
  downloading a report file.
- **F5** <br/>
  Channel is located in the *core/channel* package. Each party has list of channels to send information to.
  Each party can subscribe on channels to receive information from, which is implemented using a *hashMap* that stores
  operations and parties subscribed on them.
- **F6** <br/>
  We solve the "double spending problem" in such a way that each Party stores its certificates and cannot add a
  certificate with an existing certificate to a product that also allows performing the same action as the existing
  certificate, regardless of whether the existing certificate is active or not.
- **F7** <br/>
  To simulate retroactive change attempt(pokus o zpětnou změnu) parties have boolean isRetroactiveChange attribute,
  which is set on *TRUE* in *config3.json*.
  The *createTransaction* function checks if party wants to make such a change and if yes, party changes the previous
  transaction by setting it to *null*.
  When *isValid* function is called, it detects the difference between generated and stored hash and warns about the
  change attempt.
- **F8** <br/>
  Transition of product states is implemented using State pattern, states are located in the *core/product/state*
  package.
  In the *processProduct* function of the Party class, party sets product state to *Received*, then it changes to
  *Processing* and *Ready* state,
  after what product is ready for being published and the *publishEvent* function is called.
- **F9** <br/>
  After conducting operation, party can send a notification to the channel to inform other parties.
  Subscription on channels/operations is implemented using Observer pattern, where channels act as publishers and
  parties as subscribers.
- **F10** <br/>

## Design patterns

- **Observer** <br/>
  Communication between parties and channels.
- **State machine** <br/>
  Transition of product states.
- **Factory method** <br/>
- **Prototype** <br/>
- Creating more products, similar to existing ones.
- **Proxy** <br/>
