using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class alterPolicySourceSetCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<string>(
                name: "domain",
                table: "policySource",
                maxLength: 100,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 100,
                oldNullable: true);

            migrationBuilder.AddColumn<string>(
                name: "dataParse",
                table: "policySource",
                maxLength: 10,
                nullable: false,
                defaultValue: "RE");

            migrationBuilder.AddColumn<string>(
                name: "datacontent",
                table: "policySource",
                maxLength: 10,
                nullable: true,
                defaultValue: "RE");

            migrationBuilder.AddColumn<string>(
                name: "dateParse",
                table: "policySource",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "formData",
                table: "policySource",
                maxLength: 10,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "jsonFormat",
                table: "policySource",
                maxLength: 300,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "method",
                table: "policySource",
                maxLength: 10,
                nullable: false,
                defaultValue: "GET");

            migrationBuilder.AddColumn<int>(
                name: "pageMax",
                table: "policySource",
                nullable: false,
                defaultValue: 10);

            migrationBuilder.AddColumn<string>(
                name: "pageParse",
                table: "policySource",
                maxLength: 100,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "pageType",
                table: "policySource",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "titleParse",
                table: "policySource",
                maxLength: 100,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "dataParse",
                table: "policySource");

            migrationBuilder.DropColumn(
                name: "datacontent",
                table: "policySource");

            migrationBuilder.DropColumn(
                name: "dateParse",
                table: "policySource");

            migrationBuilder.DropColumn(
                name: "formData",
                table: "policySource");

            migrationBuilder.DropColumn(
                name: "jsonFormat",
                table: "policySource");

            migrationBuilder.DropColumn(
                name: "method",
                table: "policySource");

            migrationBuilder.DropColumn(
                name: "pageMax",
                table: "policySource");

            migrationBuilder.DropColumn(
                name: "pageParse",
                table: "policySource");

            migrationBuilder.DropColumn(
                name: "pageType",
                table: "policySource");

            migrationBuilder.DropColumn(
                name: "titleParse",
                table: "policySource");

            migrationBuilder.AlterColumn<string>(
                name: "domain",
                table: "policySource",
                maxLength: 100,
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 100);
        }
    }
}
