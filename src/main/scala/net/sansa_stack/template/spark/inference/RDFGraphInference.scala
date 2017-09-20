package net.sansa_stack.template.spark.inference

import java.io.File
import scala.collection.mutable
import org.apache.spark.sql.SparkSession
import net.sansa_stack.inference.spark.RDFGraphMaterializer
import net.sansa_stack.inference.spark.data.loader.RDFGraphLoader
import net.sansa_stack.inference.spark.forwardchaining.ForwardRuleReasonerRDFS
import net.sansa_stack.inference.rules.ReasoningProfile
import net.sansa_stack.inference.spark.forwardchaining.ForwardRuleReasonerOWLHorst
import net.sansa_stack.inference.rules.ReasoningProfile._
import net.sansa_stack.inference.spark.data.writer.RDFGraphWriter
import net.sansa_stack.rdf.spark.io.NTripleReader

object RDFGraphInference {

  def main(args: Array[String]) = {
    if (args.length < 3) {
      System.err.println(
        "Usage: RDFGraphInference <input> <output> <reasoner")
      System.err.println("Supported 'reasoner' as follows:")
      System.err.println("  rdfs                  Forward Rule Reasoner RDFS")
      System.err.println("  owl-horst             Forward Rule Reasoner OWL Horst")
      System.exit(1)
    }
    val input = args(0) //"src/main/resources/rdf.nt"
    val output = args(1) //"src/main/resources/res/"
    val argprofile = args(2) //"rdfs"

    val profile = argprofile match {
      case "rdfs"      => ReasoningProfile.RDFS
      case "owl-horst" => ReasoningProfile.OWL_HORST

    }
    val optionsList = args.drop(3).map { arg =>
      arg.dropWhile(_ == '-').split('=') match {
        case Array(opt, v) => (opt -> v)
        case _             => throw new IllegalArgumentException("Invalid argument: " + arg)
      }
    }
    val options = mutable.Map(optionsList: _*)

    options.foreach {
      case (opt, _) => throw new IllegalArgumentException("Invalid option: " + opt)
    }
    println("======================================")
    println("|        RDF Graph Inference         |")
    println("======================================")

    val sparkSession = SparkSession.builder
      .master("local[*]")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .config("spark.hadoop.validateOutputSpecs", "false") //override output files
      .config("spark.default.parallelism", "4")
      .appName(s"RDF Graph Inference ($profile)")
      .getOrCreate()

    // load triples from disk
    val graph = RDFGraphLoader.loadFromDisk(sparkSession: SparkSession, new File(input).getAbsolutePath: String, 4: Int)
    println(s"|G|=${graph.size()}")

    // create reasoner
    val reasoner = profile match {
      case RDFS      => new ForwardRuleReasonerRDFS(sparkSession.sparkContext)
      case OWL_HORST => new ForwardRuleReasonerOWLHorst(sparkSession.sparkContext)
    }

    
    // compute inferred graph
    val inferredGraph = reasoner.apply(graph)
    println(s"|G_inferred|=${inferredGraph.size()}")

    // write triples to disk
    RDFGraphWriter.writeToDisk(inferredGraph, new File(output).getAbsolutePath)

    sparkSession.stop
  }
}