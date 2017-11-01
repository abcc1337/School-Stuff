program delivery;

{ $MODE DELPHI}

var
    k, x, y, lo, hi, cnt, ans: int64;

begin
    reset(input, 'delivery.in');
    rewrite(output, 'delivery.out');

    readln(k);
    readln(x);
    readln(y);

    if y mod x <= y div x then
        ans := y
    else
        ans := (y div x + 1) * x;
    writeln(ans);

    close(input);
    close(output);
end.
