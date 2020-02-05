package org.wisdomrider.lazylibrarydemo

import java.io.Serializable

class Dataclass(var name: String, var age: Int, var address: String, var family:List<Family>)
class Family(var name: String, var relation: String): Serializable
class Dash(var data:Data, var status: String,var message: String, var status_code: Int)
class Data(var TotalLead: Int)