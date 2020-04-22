package target.client


class RedSkyResp(val product: Product)
class Product(val item: Item)
class Item(val product_description: Description)
class Description(val title: String)