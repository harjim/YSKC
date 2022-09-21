using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addT_investmentBeianIdCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "reportName",
                table: "t_beian");

            migrationBuilder.AddColumn<int>(
                name: "beianId",
                table: "t_investment",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AlterColumn<string>(
                name: "pname",
                table: "t_beian",
                maxLength: 200,
                nullable: true,
                oldClrType: typeof(string),
                oldNullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "beianId",
                table: "t_investment");

            migrationBuilder.AlterColumn<string>(
                name: "pname",
                table: "t_beian",
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 200,
                oldNullable: true);

            migrationBuilder.AddColumn<string>(
                name: "reportName",
                table: "t_beian",
                maxLength: 200,
                nullable: true);
        }
    }
}
