using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class reportRdFee : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "cnt",
                table: "p_report",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<int>(
                name: "rdFee",
                table: "p_report",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "rdFee",
                table: "p_report");

            migrationBuilder.AlterColumn<int>(
                name: "cnt",
                table: "p_report",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);
        }
    }
}
