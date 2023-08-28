Dice
=====

### Opus #26

A library for dice-based operations.

## Features

### Dice

An X-sided die rolled Y times, in the format `XdY`. Alternatively, Y X-sided dice each rolled once.

Generates a score between `X` and `X*Y` (inclusively).

Examples: `1d20`, `1d6`, `2d8`.

### Bonus

A fixed numerical bonus on dice rolls, to be used in an aggregated score.

Examples: `1`, `-3`, `5`.

### Score

An aggregate of several dice with additional bonuses.

This can be simplified into its lowest common representation (e.g. `1d11+1` = `2d6`.)

Examples: `1d6+5`, `2d8+1d4-5`.



