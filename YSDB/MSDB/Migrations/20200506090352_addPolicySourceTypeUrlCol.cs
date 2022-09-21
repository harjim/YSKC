using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addPolicySourceTypeUrlCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "typeUrl",
                table: "policySource",
                maxLength: 200,
                nullable: false,
                defaultValue: "");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "typeUrl",
                table: "policySource");
        }
    }
}
