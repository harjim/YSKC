using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterP_projectResultDefaultValueTo0 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<string>(
                name: "result",
                table: "p_project",
                maxLength: 10,
                nullable: false,
                defaultValue: "0",
                oldClrType: typeof(string),
                oldMaxLength: 10,
                oldDefaultValue: "");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<string>(
                name: "result",
                table: "p_project",
                maxLength: 10,
                nullable: false,
                defaultValue: "",
                oldClrType: typeof(string),
                oldMaxLength: 10,
                oldDefaultValue: "0");
        }
    }
}
