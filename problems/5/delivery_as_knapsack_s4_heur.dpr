program delivery;

{ $MODE DELPHI}

const
    max = 40000 * 200;

var
    k, x, y, i, j, ans: longint;
    can: array [0..max] of boolean;

begin
    reset(input, 'delivery.in');
    rewrite(output, 'delivery.out');

    readln(k);
    readln(x);
    readln(y);

    can[0] := true;

    i := 0;
    while (i < y) or not can[i] do begin
        if can[i] then begin
            for j := x to x + k - 1 do begin
                can[i + j] := true;
            end;
        end;
        inc(i);
    end;

    ans := y;
    while not can[ans] do
        inc(ans);
    writeln(ans);

    close(input);
    close(output);
end.
