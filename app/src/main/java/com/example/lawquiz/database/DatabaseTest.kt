import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lawquiz.domain.Question
import com.example.lawquiz.domain.Test

@Entity
data class DatabaseTest(
    @PrimaryKey(autoGenerate = true)
    val id : String,
    val numOfQues : Int,
    val difficulty: String,
    val category: String,
    //stores the ids of the questions
    val questions : ArrayList<String>,
    val score : Int
)

fun DatabaseTest.asDomainTest(): Test{
    return Test(
        id = this.id,
        numOfQues = this.numOfQues,
        difficulty = this.difficulty,
        category = this.category,
        questions = this.questions,
        score = this.score
    )
}