package com.rule.engine;

import com.rule.engine.evaluator.BooleanParser;
import com.rule.engine.evaluator.booleans.ExpressionNode;
import com.rule.engine.evaluator.booleans.Node;
import com.rule.engine.evaluator.booleans.OrNode;
import com.rule.engine.evaluator.booleans.ValueNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EvaluatorApplicationTests {

	@Test
	public void contextLoads() {
		ExpressionNode result = (ExpressionNode) BooleanParser.parse("A||C");
		Assert.assertTrue(result instanceof OrNode);
		Assert.assertTrue(result.getLeft() instanceof ValueNode);
		Assert.assertTrue(result.getRight() instanceof ValueNode);

	}

}
