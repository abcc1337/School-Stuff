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

    ans := y;
    writeln(ans);

    close(input);
    close(output);
end.
