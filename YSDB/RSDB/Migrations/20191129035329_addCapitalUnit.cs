using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addCapitalUnit : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "capital",
                table: "company",
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 20);

            migrationBuilder.AddColumn<string>(
                name: "capitalUnit",
                table: "company",
                maxLength: 50,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "capitalUnit",
                table: "company");

            migrationBuilder.AlterColumn<string>(
                name: "capital",
                table: "company",
                maxLength: 20,
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);
        }
    }
}
