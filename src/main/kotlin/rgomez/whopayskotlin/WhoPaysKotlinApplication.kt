package rgomez.whopayskotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@ServletComponentScan
@SpringBootApplication
class WhoPaysKotlinApplication

fun main(args: Array<String>) {
  runApplication<WhoPaysKotlinApplication>(*args)
}
