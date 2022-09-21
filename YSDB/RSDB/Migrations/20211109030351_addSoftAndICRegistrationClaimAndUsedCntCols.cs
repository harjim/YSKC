using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addSoftAndICRegistrationClaimAndUsedCntCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "claimContent",
                table: "softRegistration",
                maxLength: 1000,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<int>(
                name: "claimNum",
                table: "softRegistration",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "source",
                table: "softRegistration",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "specification",
                table: "softRegistration",
                maxLength: 2000,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "usedCnt",
                table: "softRegistration",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "claimContent",
                table: "ICRegistration",
                maxLength: 1000,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<int>(
                name: "claimNum",
                table: "ICRegistration",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "source",
                table: "ICRegistration",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "specification",
                table: "ICRegistration",
                maxLength: 2000,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "usedCnt",
                table: "ICRegistration",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "claimContent",
                table: "softRegistration");

            migrationBuilder.DropColumn(
                name: "claimNum",
                table: "softRegistration");

            migrationBuilder.DropColumn(
                name: "source",
                table: "softRegistration");

            migrationBuilder.DropColumn(
                name: "specification",
                table: "softRegistration");

            migrationBuilder.DropColumn(
                name: "usedCnt",
                table: "softRegistration");

            migrationBuilder.DropColumn(
                name: "claimContent",
                table: "ICRegistration");

            migrationBuilder.DropColumn(
                name: "claimNum",
                table: "ICRegistration");

            migrationBuilder.DropColumn(
                name: "source",
                table: "ICRegistration");

            migrationBuilder.DropColumn(
                name: "specification",
                table: "ICRegistration");

            migrationBuilder.DropColumn(
                name: "usedCnt",
                table: "ICRegistration");
        }
    }
}
