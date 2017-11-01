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

    cnt := y div x;
    lo := cnt * x;
    hi := cnt * (x + k - 1);
    if (lo <= y) and (y <= hi) then
        ans := y
    else
        ans := x * (cnt + 1);
    writeln(ans);

    close(input);
    close(output);
end.
