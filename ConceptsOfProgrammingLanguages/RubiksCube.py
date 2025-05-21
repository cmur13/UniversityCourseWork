import random

class RubiksCube:
    def __init__(self, size):
        """Initialize the Rubik's Cube with the specified size and colors."""
        self.size = size
        self.sides = {
            "Front": [['R' for _ in range(size)] for _ in range(size)],  # Red
            "Back": [['B' for _ in range(size)] for _ in range(size)],   # Blue
            "Left": [['O' for _ in range(size)] for _ in range(size)],   # Orange
            "Right": [['W' for _ in range(size)] for _ in range(size)],  # White
            "Up": [['Y' for _ in range(size)] for _ in range(size)],     # Yellow
            "Down": [['G' for _ in range(size)] for _ in range(size)],   # Green
        }

    def show(self):
        """Display the cube's current state."""
        for side in self.sides:
            print(f"{side} face:")
            for row in self.sides[side]:
                print(' '.join(row))
            print()

    def rotate_side_clockwise(self, side):
        """Rotate the specified side of the cube clockwise."""
        self.sides[side] = [list(row) for row in zip(*self.sides[side][::-1])]

    def rotate_side_counterclockwise(self, side):
        """Rotate the specified side of the cube counterclockwise."""
        self.sides[side] = [list(row) for row in zip(*self.sides[side])][::-1]

    def shift_row(self, row_index, direction):
        """Shift the specified row in the specified direction (left or right)."""
        if direction not in ['left', 'right']:
            raise ValueError("Direction must be 'left' or 'right'.")

        if row_index < 0 or row_index >= self.size:
            raise ValueError("Invalid row number")

        # Retrieve the rows involved in the shift
        front_row = self.sides["Front"][row_index]
        left_row = self.sides["Left"][row_index]
        back_row = self.sides["Back"][row_index]
        right_row = self.sides["Right"][row_index]

        if direction == 'right':
            # Shift right
            new_front_row = left_row
            new_left_row = back_row
            new_back_row = right_row
            new_right_row = front_row

            self.sides["Front"][row_index] = new_front_row
            self.sides["Left"][row_index] = new_left_row
            self.sides["Back"][row_index] = new_back_row
            self.sides["Right"][row_index] = new_right_row

            if row_index == 0:
                self.rotate_side_counterclockwise("Up")
            elif row_index == self.size - 1:
                self.rotate_side_clockwise("Down")
        else:
            # Shift left
            new_front_row = right_row
            new_left_row = front_row
            new_back_row = left_row
            new_right_row = back_row

            self.sides["Front"][row_index] = new_front_row
            self.sides["Left"][row_index] = new_left_row
            self.sides["Back"][row_index] = new_back_row
            self.sides["Right"][row_index] = new_right_row

            if row_index == 0:
                self.rotate_side_clockwise("Up")
            elif row_index == self.size - 1:
                self.rotate_side_counterclockwise("Down")

    def shift_column(self, col, direction):
        """Shift the specified column in the specified direction (up or down)."""
        if direction not in ['up', 'down']:
            raise ValueError("Direction must be 'up' or 'down'.")

        if col < 0 or col >= self.size:
            raise ValueError("Invalid column number")

        front_col = [self.sides["Front"][i][col] for i in range(self.size)]
        down_col = [self.sides["Down"][i][col] for i in range(self.size)]
        back_col = [self.sides["Back"][i][col] for i in range(self.size)]
        up_col = [self.sides["Up"][i][col] for i in range(self.size)]

        if direction == 'up':
            for i in range(self.size):
                self.sides["Front"][i][col] = down_col[i]
                self.sides["Down"][i][col] = back_col[i]
                self.sides["Back"][i][col] = up_col[self.size - 1 - i]
                self.sides["Up"][i][col] = front_col[i]

            if col == 0:
                self.rotate_side_counterclockwise("Left")
            elif col == self.size - 1:
                self.rotate_side_clockwise("Right")
        else:
            for i in range(self.size):
                self.sides["Front"][i][col] = up_col[i]
                self.sides["Down"][i][col] = front_col[i]
                self.sides["Back"][i][col] = down_col[self.size - 1 - i]
                self.sides["Up"][i][col] = back_col[i]

            if col == 0:
                self.rotate_side_clockwise("Left")
            elif col == self.size - 1:
                self.rotate_side_counterclockwise("Right")

    def randomize(self, moves=30):
        """Randomly scramble the cube using a series of row and column shifts."""
        for _ in range(moves):
            if random.choice(['row', 'column']) == 'row':
                row = random.randint(0, self.size - 1)
                direction = random.choice(['left', 'right'])
                self.shift_row(row, direction)
            else:
                col = random.randint(0, self.size - 1)
                direction = random.choice(['up', 'down'])
                self.shift_column(col, direction)

# Example usage
if __name__ == "__main__":
    rubiks = RubiksCube(3)
    rubiks.show()

    print("After scrambling:")
    rubiks.randomize(moves=30)
    rubiks.show()